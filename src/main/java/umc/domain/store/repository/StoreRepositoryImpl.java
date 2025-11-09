package umc.domain.store.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import umc.domain.store.dto.GetStoreRequest;
import umc.domain.store.dto.GetStoreResponse;
import umc.domain.store.entity.StoreSort;
import umc.domain.store.entity.QDistrict;
import umc.domain.store.entity.QStore;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<GetStoreResponse> searchStores(GetStoreRequest request, Pageable pageable) {

        QStore store = QStore.store;
        QDistrict district = QDistrict.district;

        BooleanBuilder where = buildWhere(request, store);

        OrderSpecifier<?>[] orderSpecifiers = translateSort(request.safeSort(), store);

        List<GetStoreResponse> content = queryFactory
                .select(Projections.constructor(GetStoreResponse.class,
                        store.id,
                        store.name,
                        district.name,
                        store.createdAt
                ))
                .from(store)
                .join(store.district, district)
                .where(where)
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(store.count())
                .from(store)
                .where(where)
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    private static BooleanBuilder buildWhere(GetStoreRequest request, QStore store) {
        BooleanBuilder where = new BooleanBuilder();

        // 지역구 필터
        if (request.districtIds() != null && !request.districtIds().isEmpty()) {
            where.and(store.district.id.in(request.districtIds()));
        } else if (request.districtId() != null) {
            where.and(store.district.id.eq(request.districtId()));
        }

        // 이름 검색
        if (request.keyword() != null && !request.keyword().isBlank()) {
            String[] parts = request.keyword().trim().split("\\s+");
            if (parts.length > 1) {
                BooleanExpression nameCond = Arrays.stream(parts)
                        .filter(w -> !w.isBlank())
                        .map(store.name::containsIgnoreCase)
                        .reduce(BooleanExpression::or)
                        .orElse(null);
                if (nameCond != null) {
                    where.and(nameCond);
                }
            } else {
                where.and(store.name.containsIgnoreCase(parts[0]));
            }
        }
        return where;
    }

    private NumberExpression<Integer> nameBucket(QStore store) {
        return Expressions.numberTemplate(Integer.class,
                "CASE " +
                        "WHEN SUBSTRING({0},1,1) BETWEEN '가' AND '힣' THEN 0 " +
                        "WHEN SUBSTRING({0},1,1) BETWEEN 'A' AND 'Z' THEN 1 " +
                        "WHEN SUBSTRING({0},1,1) BETWEEN 'a' AND 'z' THEN 2 " +
                        "ELSE 3 END",
                store.name);
    }

    private OrderSpecifier<?>[] translateSort(StoreSort sort, QStore store) {

        if (sort == StoreSort.NAME) {
            return new OrderSpecifier<?>[]{
                    nameBucket(store).asc(),                                                 // 가→A→a→기타
                    Expressions.booleanTemplate("{0} IS NULL", store.name).asc(),           // NULLS LAST
                    Expressions.stringTemplate("{0} COLLATE utf8mb4_bin", store.name).asc(),// 대/소문자 구분 정렬
                    store.createdAt.desc(),                                                  // 동명이인 최신순
                    store.id.desc()
            };
        }

        // default: latest
        return new OrderSpecifier<?>[]{
                store.createdAt.desc(),
                store.id.desc()
        };
    }
}
