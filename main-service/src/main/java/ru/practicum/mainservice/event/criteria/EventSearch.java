package ru.practicum.mainservice.event.criteria;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.practicum.mainservice.event.Event;
import ru.practicum.mainservice.location.LocationEntity_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class EventSearch {

    @PersistenceContext
    private EntityManager em;

    // Получение все опубликованных событий в радиусе указанной локации
    public List<Event> findEventsInLocation(double lat, double lon, long radius, PageRequest pageRequest) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);
        Root<Event> root = cq.from(Event.class);
        cq.select(root);

        ParameterExpression<Double> latPar = cb.parameter(Double.class);
        ParameterExpression<Double> lonPar = cb.parameter(Double.class);
        ParameterExpression<Double> radiusPar = cb.parameter(Double.class);

        cq.where(cb.greaterThan(radiusPar, cb.function("distance", Double.class, latPar, lonPar,
                root.get(LocationEntity_.LAT),
                root.get(LocationEntity_.LON))));

        TypedQuery<Event> tq = em.createQuery(cq);


        tq.setParameter(latPar, lat);
        tq.setParameter(lonPar, lon);
        Double rad = (double) radius / 1000;
        tq.setParameter(radiusPar, rad);

        tq.setFirstResult(pageRequest.getPageNumber());
        tq.setMaxResults(pageRequest.getPageSize());
        return tq.getResultList();
    }
}
