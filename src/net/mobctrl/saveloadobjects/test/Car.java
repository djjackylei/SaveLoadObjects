package net.mobctrl.saveloadobjects.test;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @date 2014年12月11日 下午5:17:58
 * @author Zheng Haibo
 * @Description: Java Bean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Car implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 672030801450951839L;
	
	@JsonProperty
	private int id;
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String []wheels;
	
	@JsonProperty
	private String []colors;
	
	@JsonProperty
	private long distance;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getWheels() {
		return wheels;
	}

	public void setWheels(String[] wheels) {
		this.wheels = wheels;
	}

	public String[] getColors() {
		return colors;
	}

	public void setColors(String[] colors) {
		this.colors = colors;
	}

	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}

	public Car(int id, String name, String[] wheels, String[] colors,
			long distance) {
		super();
		this.id = id;
		this.name = name;
		this.wheels = wheels;
		this.colors = colors;
		this.distance = distance;
	}
	
	public Car(){
		
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", name=" + name + ", wheels="
				+ Arrays.toString(wheels) + ", colors="
				+ Arrays.toString(colors) + ", distance=" + distance + "]";
	}
	
}
