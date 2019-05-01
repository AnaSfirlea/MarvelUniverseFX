package sample;

import dataReader.DataReader;
import fightingComponents.Character;
import fightingComponents.Planet;
import fightingLogic.Fight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Controller {

    private Fight fight = new Fight();

    private ObservableList<String> avengersTeam = FXCollections.observableArrayList();

    @FXML
    ImageView heroesImage;

    @FXML
    ImageView villainImage;

    @FXML
    ImageView planetImage;

    @FXML
    Label villainsLabel;

    @FXML
    Label heroesLabel;

    @FXML
    Label planetsLabel;

    @FXML
    Button btnHero;

    @FXML
    Button btnVillain;

    @FXML
    Button btnPlanet;

    @FXML
    Button btnFight;

    @FXML
    ListView<Character> heroesListView;

    @FXML
    ListView<Character> villainsListView;

    @FXML
    ListView<Planet> planetsListView;

    @FXML
    ListView<String> avengersListView;

    @FXML
    void chooseHero(ActionEvent event) {
        try{
        Character hero = heroesListView.getSelectionModel().getSelectedItem();
        int flag = this.fight.addAvenger(hero);

        if(flag == 1)
            avengersTeam.add(hero.getName());

        this.avengersListView.setItems(avengersTeam);
        }

        catch(NullPointerException ex)
        {
            System.out.println("You have to select at least 1 hero");
        }

    }

    @FXML
    void chooseVillain(ActionEvent event) {
        try{
            Character villain = villainsListView.getSelectionModel().getSelectedItem();
            System.out.println(villain.getName());
            this.fight.setVillain(villain);
        }
        catch(NullPointerException ex)
        {
            System.out.println("You have to select 1 villain");
        }
    }

    @FXML
    void choosePlanet(ActionEvent event) {
        try{
            Planet planet = planetsListView.getSelectionModel().getSelectedItem();
            System.out.println(planet.getName());
            this.fight.setPlanet(planet);
        }
        catch(NullPointerException ex)
        {
            System.out.println("You have to select 1 planet");
        }
    }

    @FXML
    void fightBtn(ActionEvent event)
    {
        try{
            this.fight.beginFight();
        }
        catch(Exception ex)
        {
            System.out.println("Something went wrong: "+ex.getMessage());
        }
    }
    @FXML
    private void initialize(){
        populateListsView();
        setImagesForSelectedItems();


    }

    private void populateListsView() {
        DataReader dr = new DataReader();
        dr.readCharacters();
        dr.readPlanets();

        ObservableList<Character> heroes = FXCollections.observableArrayList();

        heroes.addAll(dr.getHeroes());

        heroesListView.setItems(heroes);

        ObservableList<Character> villains = FXCollections.observableArrayList();

        villains.addAll(dr.getVillains());

        villainsListView.setItems(villains);

        ObservableList<Planet> planets = FXCollections.observableArrayList();

        planets.addAll(dr.getPlanets());

        planetsListView.setItems(planets);
    }


    private void setImagesForSelectedItems()
    {
        heroesListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                File file = new File(heroesListView.getSelectionModel().getSelectedItem().getImagePath());
                Image image = new Image(file.toURI().toString());
                heroesImage.setImage(image);
            }

        });

        villainsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                File file = new File(villainsListView.getSelectionModel().getSelectedItem().getImagePath());
                Image image = new Image(file.toURI().toString());
                villainImage.setImage(image);
            }

        });

        planetsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                File file = new File(planetsListView.getSelectionModel().getSelectedItem().getImagePath());
                Image image = new Image(file.toURI().toString());
                planetImage.setImage(image);
            }

        });
    }

}

