package com.sushchenko.mystictourismapp.repo.specification;

import com.sushchenko.mystictourismapp.entity.Place;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;
public class PlaceSpecification {
    public static Specification<Place> filterPlaces(Double ratingStart, Double ratingEnd,
                                                    String name, Set<String> tags) {
        Specification<Place> spec = Specification.where(null);
        Double ratingStartValue = ratingStart != null ? ratingStart : Double.valueOf(0);
        Double ratingEndValue = ratingEnd != null ? ratingEnd : Double.valueOf(5);
        if(name != null && !name.isEmpty()) {
            spec = spec.and(nameLike(name));
        }
        if(tags != null && !tags.isEmpty()) {
            spec = spec.and(tagsIn(tags));
        }
        spec = spec.and(ratingBetween(ratingStartValue, ratingEndValue));
        return spec;
    }
    private static Specification<Place> ratingBetween(Double ratingStart, Double ratingEnd) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("rating"), ratingStart, ratingEnd));
    }
    private static Specification<Place> nameLike(String name) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%"));
    }
    private static Specification<Place> tagsIn(Set<String> tags) {
        return (((root, query, criteriaBuilder) -> {
            Expression<String> placeTags = root.join("tags").get("id").get("tag");
            Predicate tagsPredicate = placeTags.in(tags);
            return criteriaBuilder.and(tagsPredicate);
        }));
    }
}
