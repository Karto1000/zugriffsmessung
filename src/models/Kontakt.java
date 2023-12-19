package models;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Kontakt {
    private Integer kid;
    private String name;
    private String nameI;
    private String vorname;
    private String strasse;
    private String email;
    private String tpriv;
    private String tgesch;
    private Integer plz;
    private String ort;
}
