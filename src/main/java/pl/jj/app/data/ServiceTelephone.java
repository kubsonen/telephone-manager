package pl.jj.app.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jj.app.model.Telephone;
import pl.jj.app.util.Const;
import pl.jj.app.util.ShowMode;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @author JNartowicz
 */
@Service
public class ServiceTelephone {

    @Autowired
    private RepositoryTelephone repositoryTelephone;

    @Transactional
    public void addTelephone(Telephone telephone){
        telephone.setPhoneDate(new Date());
        telephone.setCreateTime(new Date());
        repositoryTelephone.save(telephone);
    }

    @Transactional
    public List<Telephone> findTop10Telephones(){
        return repositoryTelephone.findTop10ByOrderByIdDesc();
    }

    @Transactional
    public void removeTelephone(Long id){
        repositoryTelephone.deleteById(id);
    }

    @Transactional
    public void deleteAllTelephones(){
        repositoryTelephone.deleteAll();
    }

    @Transactional
    public List<Telephone> getFilteredTelephones(Integer page, ShowMode showMode, Integer showsOnPage, Integer downLimit, Integer upLimit){
        Date endDate = Const.modifyTimeOfDate(new Date(), true);
        Date startDate = getStartDateByShowMode(showMode, endDate);
        return repositoryTelephone.filterTelephones( startDate, endDate, downLimit, upLimit);
    }

    @Transactional
    public int getCountOfFilteredTelephones(ShowMode showMode){
        Date endDate = Const.modifyTimeOfDate(new Date(), true);
        Date startDate = getStartDateByShowMode(showMode, endDate);
        return repositoryTelephone.getCountFilterTelephones(startDate, endDate);
    }

    private Date getStartDateByShowMode(ShowMode showMode, Date endDate){
        Date startDate = null;
        switch (showMode){
            case DAY:
                startDate = Const.modifyTimeOfDate(endDate, false);
                break;
            case WEEK:
                startDate = Const.modifyTimeOfDate(Const.getFirstDayOfTheWeek(endDate), false);
                break;
            case MONTH:
                startDate = Const.modifyTimeOfDate(Const.getFirstDayOfTheMonth(endDate), false);
                break;
            case YEAR:
                startDate = Const.modifyTimeOfDate(Const.getFirstDayOfTheYear(endDate), false);
                break;
            default:
                return null;
        }
        return startDate;
    }

    @Transactional
    public Set<Object[]> getStatsTelephonesByDays(Integer days){

        //Prepare date from
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, Math.abs(days) * (-1));
        Date date = Const.modifyTimeOfDate(calendar.getTime(), false);

        //Select data
        Set<Object[]> data = repositoryTelephone.telephonesFromDate(date);

        if(data == null || data.isEmpty()) return null;
        return data;

    }

}
