package com.company;

public class Main {

    public static void main(String[] args) {
        User user1 =new User("Bakytzhan","dekim5262@gmail.com","admin");
        User user2 =new User("Ualihan","uali@gmail.com","admin");
        User user3 =new User("Biba","biba@gmail.com","");

        UserManager userManager = new UserManager();
        userManager.AddUser(user1);
        userManager.AddUser(user2);
        userManager.AddUser(user3);
        userManager.ShowUsers();
        userManager.UpdateUser(2,"Beibarys","beibarys@gmail.com","admin");
        System.out.println(user3);
        userManager.DeleteUser(1);
        userManager.ShowUsers();

    }
}
package com.company;

public class User {
    private String name;
    private String email;
    private String role;

    public User(String name,String email,String role){
        this.name=name;
        this.email=email;
        this.role=role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
package com.company;

import java.util.ArrayList;

public class UserManager {
    private ArrayList<User>users;

    public UserManager(){
        users=new ArrayList<>();

    }
    public void AddUser(User user){
        users.add((user));
        System.out.println("Пользователь добавлен");
    }

    public void DeleteUser(int index){
        if(index>=0 && index<=users.size()){
            users.remove(index);
            System.out.println("Пользователь удален");
        }
    }
    public void UpdateUser(int index, String newName, String newEmail, String newRole) {
        if (index >= 0 && index < users.size()) {
            User user = users.get(index);
            user.setName(newName);
            user.setEmail(newEmail);
            user.setRole(newRole);
            System.out.println("Данные пользователя обновлены");
        } else {
            System.out.println("Неверный индекс");
        }
    }
    public void ShowUsers() {
        for (int i = 0; i < users.size(); i++) {
            System.out.println(i + ": " + users.get(i));
        }
    }




}
