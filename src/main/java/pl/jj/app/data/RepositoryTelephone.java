package pl.jj.app.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.jj.app.entity.Telephone;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author JNartowicz
 */
public interface RepositoryTelephone extends CrudRepository<Telephone, Long> {
    List<Telephone> findTop10ByOrderByIdDesc();

    String filterTelephoneBaseQuery = "" +
            " from (" +
            " select ROW_NUMBER() OVER () AS R, t.* from (select * from telephone t " +
            " where t.phone_date > :createTimeStart and t.phone_date < :createTimeEnd " +
            " order by t.phone_date desc) as t ";

    String filterTelephoneLimitRowQuery = " ) as tr " +
            " where R >= :downLimit and R <= :upLimit";

    String getTelephonesFromDate = " select a.date as date, count(a.id) as quantity from (" +
            "select t.id as id, DATE(t.phone_date) as date from telephone t " +
            "where t.phone_date > :phoneDateFrom " +
            ") a group by a.date order by a.date desc";

    @Query(value = "select count(*) " + filterTelephoneBaseQuery + ") as tr", nativeQuery = true)
    int getCountFilterTelephones(@Param("createTimeStart") Date startTime,
                                 @Param("createTimeEnd") Date endTime);

    @Query(value = "select * " + filterTelephoneBaseQuery + filterTelephoneLimitRowQuery, nativeQuery = true)
    List<Telephone> filterTelephones(@Param("createTimeStart") Date startTime,
                                     @Param("createTimeEnd") Date endTime,
                                     @Param("downLimit") Integer downLimit,
                                     @Param("upLimit") Integer upLimit);

    @Query(value = getTelephonesFromDate, nativeQuery = true)
    Set<Object[]> telephonesFromDate(@Param("phoneDateFrom") Date date);


}
