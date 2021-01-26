package li.haomin.whale.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String password;
    private String id_no;
    private String birth;
    private String sex;
    private String address;
    private String email;
    private String mobile;
    private String datetime;
}
