package it.dedagroup.biglietto.repository;

import it.dedagroup.biglietto.model.Biglietto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class BigliettoCriteriaQuery {
    private final EntityManager manager;

    public List<Biglietto> getBigliettiFiltrati(Map<String,String> parametriBiglietto){
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createQuery(Tuple.class);
        Root<Biglietto> root = query.from(Biglietto.class);

        List<Predicate> predicate = new ArrayList<>();
        if (parametriBiglietto==null) parametriBiglietto = new HashMap<>();
        for (String nomeParametro: parametriBiglietto.keySet()){
            Predicate p = builder.like(root.get(nomeParametro).as(String.class), "%"+parametriBiglietto.get(nomeParametro)+"%");
            predicate.add(p);
        }
        Predicate[] predicateArray = predicate.toArray(new Predicate[predicate.size()]);
        query.multiselect(root).where(predicateArray);
        List<Tuple> list = manager.createQuery(query).getResultList();
        return list.stream().map(t -> t.get(0,Biglietto.class)).toList();
    }
}
