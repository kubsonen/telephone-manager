package pl.jj.app.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.jj.app.entity.Telephone;

import java.util.List;

/**
 * @author JNartowicz
 */
public interface RepositoryTelephone extends CrudRepository<Telephone, Long> {
    List<Telephone> findTop10ByOrderByIdDesc();

    String filterTelephoneQuery = "" +
            "select * from telephone t " +
            " order by t.phone_date desc FETCH FIRST :rowsCount ROWS ONLY";

    @Query(value = filterTelephoneQuery, nativeQuery = true)
    List<Telephone> filterTelephones(@Param("rowsCount") Integer rowsCount);


}
