import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    private TextField tfAnnualInterestRate = new TextField();
    private TextField tfNumberOfYears = new TextField();
    private TextField tfLoanAmount = new TextField();
    private TextField tfMonthlyPayment = new TextField();
    private TextField tfTotalPayment = new TextField();
    private Button btCalculate = new Button("Calculate");
    private Button btClear = new Button("Clear");
    private Button btLoad = new Button("Load");
    private Button btSave = new Button("Save");
  
  @Override
  public void start(Stage primaryStage){
    //---------------------------------------------------Create GUI------------------------------------------------------//
    GridPane gridPane = new GridPane();
    gridPane.setHgap(5);
    gridPane.setVgap(5);
    gridPane.add(new Label("Annual Interest Rate: "), 0, 0);
    gridPane.add(tfAnnualInterestRate, 1, 0);
    gridPane.add(new Label("Number of Years: "), 0, 1);
    gridPane.add(tfNumberOfYears, 1, 1);
    gridPane.add(new Label("Loan Amount: "), 0, 2);
    gridPane.add(tfLoanAmount, 1, 2);
    gridPane.add(new Label("Monthly Payment: "), 0, 3);
    gridPane.add(tfMonthlyPayment, 1, 3);
    gridPane.add(new Label("Total Payment: "), 0, 4);
    gridPane.add(tfTotalPayment, 1, 4);
    gridPane.add(btCalculate, 3, 5);
    gridPane.add(btClear, 2, 5);
    gridPane.add(btLoad, 0, 5);
    gridPane.add(btSave, 0, 5);
    //----------------------------------------------------------------------------------------------------------------//

    //-------------------------------------------------Set properties-------------------------------------------------//
    gridPane.setAlignment(Pos.CENTER);
    tfAnnualInterestRate.setAlignment(Pos.BOTTOM_RIGHT);
    tfNumberOfYears.setAlignment(Pos.BOTTOM_RIGHT);
    tfLoanAmount.setAlignment(Pos.BOTTOM_RIGHT);
    tfMonthlyPayment.setAlignment(Pos.BOTTOM_RIGHT);
    tfTotalPayment.setAlignment(Pos.BOTTOM_RIGHT);
    tfMonthlyPayment.setEditable(false);
    tfTotalPayment.setEditable(false);
    GridPane.setHalignment(btCalculate, HPos.RIGHT);
    GridPane.setHalignment(btClear, HPos.RIGHT);
    GridPane.setHalignment(btSave, HPos.LEFT);
    GridPane.setHalignment(btLoad, HPos.RIGHT);
    //---------------------------------------------------------------------------------------------------------------//

    //----------------------------------------------------events-----------------------------------------------------//
    btCalculate.setOnAction(e -> calculateLoanPayment());
    btClear.setOnAction(e -> clearFilter());
    btSave.setOnAction(e -> {
      try {
        saveInput();
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    });
      
    btLoad.setOnAction(e -> {
        try {
          loadInput();
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      });
    //-------------------------------------------------------------------------------------------------------------//

    //--------------------------------------------------Create a scene--------------------------------------------------//
    Scene scene = new Scene(gridPane, 450, 250);
    primaryStage.setTitle("LoanCalculator");
    primaryStage.setScene(scene);
    primaryStage.show();
    //-----------------------------------------------------------------------------------------------------------------//
  }
  
  //------------------------------------------------calculate LoanPayment------------------------------------------------//
  private void calculateLoanPayment(){
    //Get values from text fields
    double interest = Double.parseDouble(tfAnnualInterestRate.getText());
    double year = Double.parseDouble(tfNumberOfYears.getText());
    double loanAmount = Double.parseDouble(tfLoanAmount.getText());
    //Create a loan object
    Loan loan = new Loan(interest, year, loanAmount);

    //Display monthly payment and total payment
    tfMonthlyPayment.setText(String.format("%.2f", loan.getMonthlyPayment()));
    tfTotalPayment.setText(String.format("%.2f", loan.getTotalPayment()));
  }
  //--------------------------------------------------------------------------------------------------------------------//

  //--------------------------------------------------clear text fields--------------------------------------------------//
  public void clearFilter(){
    tfAnnualInterestRate.clear();
    tfNumberOfYears.clear();
    tfLoanAmount.clear();
    tfMonthlyPayment.clear();
    tfTotalPayment.clear();
  }
  //-------------------------------------------------------------------------------------------------------------------//

  //----------------------------------------------------Save button----------------------------------------------------//
  private void saveInput() throws IOException{
    //Get values from text fields
    double interest = Double.parseDouble(tfAnnualInterestRate.getText());
    double year = Double.parseDouble(tfNumberOfYears.getText());
    double loanAmount = Double.parseDouble(tfLoanAmount.getText());
    //double monthly = Double.parseDouble(tfMonthlyPayment.getText());
    //double total = Double.parseDouble(tfTotalPayment.getText());

    try (
      //Create output stream to the file
      ObjectOutputStream output = new ObjectOutputStream(new 
				BufferedOutputStream(new FileOutputStream("Loan.dat")));
    ) {
      //Output values to the file
        output.writeDouble(interest);
        output.writeDouble(year);
        output.writeDouble(loanAmount);
        //output.writeDouble(monthly);
        //output.writeDouble(total);
    }
  }
  //------------------------------------------------------------------------------------------------------------------//

  //---------------------------------------------------Load button---------------------------------------------------//
  private void loadInput() throws IOException{
    try (
			ObjectInputStream input = new ObjectInputStream(new 
				BufferedInputStream(new FileInputStream("Loan.dat")))
		) {
      tfAnnualInterestRate.setText(String.format("%.0f",input.readDouble()));
      tfNumberOfYears.setText(String.format("%.0f", input.readDouble()));
      tfLoanAmount.setText(String.format("%.0f", input.readDouble()));
      //tfMonthlyPayment.setText(String.format("%.2f", input.readDouble()));
      //tfTotalPayment.setText(String.format("%.2f", input.readDouble()));
			}
	}
  //----------------------------------------------------------------------------------------------------------------//
  
  public static void main(String[] args) {
    launch(args);
  }
}