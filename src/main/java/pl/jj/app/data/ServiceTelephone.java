package pl.jj.app.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jj.app.entity.Telephone;
import pl.jj.app.util.Const;
import pl.jj.app.util.ShowMode;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public List<Telephone> getFilteredTelephones(Integer page, ShowMode showMode, Integer showsOnPage){

        //End date
        Date endDate = Const.modifyTimeOfDate(new Date(), true);
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
                return new ArrayList<>();
        }

        return repositoryTelephone.filterTelephones(showsOnPage, startDate, endDate);

    }

}
