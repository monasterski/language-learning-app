package pl.edu.agh.languagelearningclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class WordWithTranslation {
    private String word;
    private String first;
    private String second;
    private String third;

    public WordWithTranslation(String word) {
        this.word = word;

    }
}
