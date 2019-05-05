package pl.jj.app.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jj.app.model.Dictionary;
import pl.jj.app.util.Const;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author JNartowicz
 */
@Service
public class ServiceDictionary {

    @Autowired
    private RepositoryDictionary repositoryDictionary;

    @Transactional
    public Dictionary addDictionary(String name, String value){
        return addDictionary(name, value, null);
    }

    @Transactional
    public Dictionary addDictionary(String name, String value, Dictionary parent){
        Dictionary d = new Dictionary();
        System.out.println("Dodanie słownika: " + name + ", wartość: " + value );
        d.setCreateTime(new Date());
        d.setName(name);
        d.setValue(value);
        d.setParent(parent);
        return repositoryDictionary.save(d);
    }

    @Transactional
    public void initializeDefaultDictionaries(){
        //Find default fields
        List<String> fields = Const.getFieldValuesByPrefix(Const.DEFAULT_DIC_FIELD_PREFIX);
        for(String dicName: fields){
            Dictionary dictionary = repositoryDictionary.findByName(dicName);
            if(dictionary == null){
                //Parent dictionary
                Dictionary parentDic = addDictionary(dicName, null);
                //Find default values for fields
                List<String> defValues = Const.getFieldValuesByPrefix(Const.DIC_VALUE_PREFIX + dicName);
                for(String val: defValues){
                    addDictionary(val, null, parentDic);
                }
            }
        }
    }

    @Transactional
    public List<Dictionary> getDictionariesByParentName(String name){
        List<Dictionary> dictionaries = new ArrayList<>();
        Dictionary dictionary = repositoryDictionary.findByName(name);
        if(dictionary != null){
            dictionary.getChildren().size();
            dictionaries.addAll(dictionary.getChildren());
        }
        return dictionaries;
    }

}
