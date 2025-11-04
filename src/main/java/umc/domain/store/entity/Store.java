    package umc.domain.store.entity;

    import jakarta.persistence.*;
    import lombok.AccessLevel;
    import lombok.Builder;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.NonNull;
    import umc.domain.member.entity.Member;
    import umc.domain.mission.entity.Mission;
    import umc.global.entity.BaseEntity;

    import java.util.ArrayList;
    import java.util.List;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Entity
    @Table(name = "stores")
    public class Store extends BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "owner_id", nullable = false, unique = true)
        private Member owner;

        @Column(nullable = false, length = 100)
        private String name;

        @Column(nullable = false, length = 200)
        private String address;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "district_id", nullable = false)
        private District district;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 20)
        private StoreType type;

        @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<StoreBusinessHour> businessHours = new ArrayList<>();

        @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
        @OrderBy("sortOrder ASC, id ASC")
        private List<StoreImage> images = new ArrayList<>();

        @OneToMany(mappedBy = "store")
        private List<Mission> missions = new ArrayList<>();

        @Builder
        public Store(
                @NonNull StoreType type,
                @NonNull String address,
                @NonNull Member owner,
                @NonNull String name,
                @NonNull District district
        ) {
            this.type = type;
            this.address = address;
            this.owner = owner;
            this.name = name;
            this.district = district;
        }

    }
