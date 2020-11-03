package game.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "type")
@XmlSeeAlso({MoveCityBlock.class, PutCityBlock.class})
public interface Command {

}
