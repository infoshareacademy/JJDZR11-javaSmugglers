package pl.isa.javasmugglers;


public class App 
{
    public static void main( String[] args ){
        //testowe odpalenie aplikacji
        Menu menu = new Menu();
        System.out.println(menu.getMenu());
        menu.userSelection(menu.getValidInput());
    }
}
