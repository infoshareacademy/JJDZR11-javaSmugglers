package pl.isa.javasmugglers;


public class App 
{
    public static void main( String[] args ){
        //testowe odpalenie aplikacji
        MenuGlowne menuGlowne = new MenuGlowne();
        System.out.println(menuGlowne.getMenu());
        menuGlowne.userSelection(menuGlowne.getValidInput());
    }
}
