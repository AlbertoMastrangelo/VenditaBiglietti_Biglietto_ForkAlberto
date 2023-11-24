package it.dedagroup.biglietto.path;

public class UtilPath {
    private static final String BIGLIETTO_PATH = "/biglietto";
    public static final String INSERT_BIGLIETTO_PATH = BIGLIETTO_PATH+"/save";
    public static final String MODIFY_BIGLIETTO_PATH = BIGLIETTO_PATH+"/modify";
    public static final String DELETE_BIGLIETTO_PATH = BIGLIETTO_PATH+"/delete/id";
    public static final String FIND_BY_ID_PATH = BIGLIETTO_PATH+"/find/id";
    public static final String FIND_ALL_PATH = BIGLIETTO_PATH+"/find/all";
    public static final String FIND_BY_SERIALE_PATH = BIGLIETTO_PATH+"/find/seriale";
    public static final String FIND_ALL_BY_PREZZO_IS_GREATER_THAN_EQUAL_PATH = BIGLIETTO_PATH+"/find/all/prezzo/greater";
    public static final String FIND_ALL_BY_PREZZO_IS_LESS_THAN_EQUAL_PATH = BIGLIETTO_PATH+"/find/all/prezzo/less";
    public static final String FIND_ALL_BY_ID_UTENTE_PATH = BIGLIETTO_PATH+"/find/all/utente/id";
    public static final String FIND_ALL_BY_DATA_ACQUISTO = BIGLIETTO_PATH+"/find/all/data-acquisto";
    public static final String FIND_ALL_BY_ID_PREZZO_SETTORE_EVENTO_PATH = BIGLIETTO_PATH+"/find/all/prezzo-settore-evento/id";
    public static final String COUNT_BY_ID_PREZZO_SETTORE_EVENTO_AND_DATA_ACQUISTO_IS_NOT_NULL = BIGLIETTO_PATH+"/count/all/prezzo-settore-evento/id/data-acquisto/not-null";
    public static final String FIND_DISTINCT_PREZZO_BIGLIETTO_PATH = BIGLIETTO_PATH+"/find/distinct/prezzo/prezzo-settore-evento";
    public static final String FIND_ALL_BY_ID_PREZZO_SETTORE_EVENTO_IN_IDS = BIGLIETTO_PATH+"/find/all/prezzo-settore-evento/in/ids";
    public static final String FIND_ALL_BIGLIETTI_FILTRATI = BIGLIETTO_PATH+"/find/all/biglietti/filtrati";
    public static final String FIND_BY_ID_AND_NOT_CANCELLATO_PATH = BIGLIETTO_PATH+"/find/id/not-cancellato";
    public static final String FIND_ALL_NOT_CANCELLATO_PATH = BIGLIETTO_PATH+"/find/all/not-cancellato";
    public static final String FIND_BY_SERIALE_AND_NOT_CANCELLATO_PATH = BIGLIETTO_PATH+"/find/seriale/not-cancellato";
    public static final String FIND_ALL_BY_PREZZO_IS_GREATER_THAN_EQUAL_AND_NOT_CANCELLATO_PATH = BIGLIETTO_PATH+"/find/prezzo/greater-than-equal/not-cancellato";
    public static final String FIND_ALL_BY_PREZZO_IS_LESS_THAN_EQUAL_AND_NOT_CANCELLATO_PATH = BIGLIETTO_PATH+"/find/all/prezzo/less-than-equal/not-cancellato";
    public static final String FIND_ALL_BY_ID_UTENTE_AND_NOT_CANCELLATO_PATH = BIGLIETTO_PATH+"/find/all/utente/id/not-cancellato";
    public static final String FIND_ALL_BY_DATA_ACQUISTO_AND_NOT_CANCELLATO_PATH = BIGLIETTO_PATH+"/find/all/data-acquisto/not-cancellato";
}
