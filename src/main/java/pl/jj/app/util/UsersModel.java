package pl.jj.app.util;

import java.util.List;

public class UsersModel {

    private List<UserModel> userModels;

    public List<UserModel> getUserModels() {
        return userModels;
    }

    public void setUserModels(List<UserModel> userModels) {
        this.userModels = userModels;
    }

    //    public List<Long> getSelectedUserIds(){
//        List<Long> ids = new ArrayList<>();
//        if(userModels != null && !userModels.isEmpty()){
//            for(UserModel user: userModels){
//                if(user.isSelected()){
//                    ids.add(user.getId());
//                }
//            }
//        }
//        return ids;
//    }
}
