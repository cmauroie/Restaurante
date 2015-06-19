package app.restaurante.com.restaurante;

/**
 * Created by Carlos Mauricio Idárraga Espitia on 17/06/2015.
 */
public class Categoria {

    private String name;
    private String img_path;

    public Categoria() {
    }

    public Categoria(String name, String img_path){
        this.name = name;
        this.img_path = img_path;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

}
