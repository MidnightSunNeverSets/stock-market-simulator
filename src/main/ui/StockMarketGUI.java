package ui;

import model.Market;
import model.Portfolio;
import model.Stock;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StockMarketGUI implements ActionListener {

    // GUI COMPONENTS
    private static final String[] COMPANIES = {"Shibe Inc.", "Papaya", "Tweety", "GuCCe", "MIYO", "Yoko"};
    private static final String BACKGROUND_IMAGE = "./data/bouncy cat.gif";
    private static final Map<String, ImageIcon> IMAGES = createImageMap();

    private JFrame frame;

    private JPanel introPanel;
    private JPanel stockListingsPanel;
    private JPanel commandsPanel;
    private JPanel mainPanel;
    private JPanel purchaseAndSellPanel;
    private JPanel stockDetailPanel;
    private JPanel allStockDetailsPanel;
    private JPanel balancePanel;
    private JPanel portfolioPanel;
    private JPanel backPanel;

    private JLabel background;
    private JLabel companyImage;

    // SIMULATOR FIELDS
    private static final String JSON_PORTFOLIO = "./data/portfolio.json";
    private static final String JSON_MARKET = "./data/market.json";

    private final JsonWriter portfolioJsonWriter;
    private final JsonWriter marketJsonWriter;
    private final JsonReader jsonReader;

    private Market stockMarket;
    private Portfolio portfolio;
    private boolean keepPlaying;



    // EFFECTS: sets up an interface
    public StockMarketGUI() {
        super();
        stockMarket = new Market(COMPANIES);
        portfolio = new Portfolio();
        portfolioJsonWriter = new JsonWriter(JSON_PORTFOLIO);
        marketJsonWriter = new JsonWriter(JSON_MARKET);
        jsonReader = new JsonReader(JSON_PORTFOLIO, JSON_MARKET);

        backPanel = new JPanel();
        backPanel.add(new JButton("BACK"));

        setUpFrame();
        createIntroPanel();
        frame.add(introPanel);
        frame.add(backPanel, BorderLayout.SOUTH);
        frame.pack();
    }

    // EFFECTS: returns map of all the images used
    private static Map<String, ImageIcon> createImageMap() {
        Map<String, ImageIcon> imageMap = new HashMap<>();
        imageMap.put(COMPANIES[0], new ImageIcon("./data/shibe.png"));
        imageMap.put("Papaya", new ImageIcon("./data/papaya.png"));
        imageMap.put("Tweety", new ImageIcon("./data/seagull.png"));


        return imageMap;
    }

    // MODIFIES: this
    // EFFECTS: TODO
    private void setUpFrame() {
        frame = new JFrame("Stock Market Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: TODO
    private void createIntroPanel() {
        introPanel = new JPanel();
        introPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        introPanel.setPreferredSize(new Dimension(180, 100));

        JButton continueGameBtn = new JButton("Continue Game"); // TODO: add action listener
        JButton newGameBtn = new JButton("New Game"); // TODO: add action listener

        introPanel.add(continueGameBtn);
        introPanel.add(newGameBtn);
    }

    // MODIFIES: this
    // EFFECTS: TODO
    private void createStockListingsPanel() {
        stockListingsPanel = new JPanel();
        JButton[] stocksBtns = new JButton[COMPANIES.length]; // TODO: add action listener
        JLabel listings = new JLabel("Current Stock Listings: ");
        stockListingsPanel.setPreferredSize(new Dimension(620, 40));

        stockListingsPanel.add(listings);

        for (int i = 0; i < COMPANIES.length; i++) {
            stocksBtns[i] = new JButton(COMPANIES[i]);
            stockListingsPanel.add(stocksBtns[i]);
        }

    }

    // MODIFIES: this
    // EFFECTS: TODO
    private void createCommandsPanel() {
        commandsPanel = new JPanel();

        commandsPanel.setLayout(new BoxLayout(commandsPanel, BoxLayout.Y_AXIS));
//        commandsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        commandsPanel.setPreferredSize(new Dimension(200, 240));

        JButton[] commandBtns = new JButton[7]; // TODO: add action listener

        commandBtns[0] = new JButton("SEE ALL STOCK DETAILS");
        commandBtns[1] = new JButton("SEE BALANCE");
        commandBtns[2] = new JButton("PURCHASE SHARES");
        commandBtns[3] = new JButton("SELL SHARES");
        commandBtns[4] = new JButton("SEE PORTFOLIO");
        commandBtns[5] = new JButton("START A NEW DAY");
        commandBtns[6] = new JButton("QUIT GAME");

        commandsPanel.add(Box.createVerticalStrut(10));
        for (JButton btn : commandBtns) {
            commandsPanel.add(btn);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            commandsPanel.add(Box.createVerticalStrut(5));
        }
    }

    // MODIFIES: this
    // EFFECTS: TODO
    private void createMainPanel() {
        createCommandsPanel();
        createStockListingsPanel();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(stockListingsPanel);
        mainPanel.add(commandsPanel);
    }

    // MODIFIES: this
    // EFFECTS: TODO
    private void createPurchaseOrSellPanel(boolean purchase) {
        purchaseAndSellPanel = new JPanel();
        purchaseAndSellPanel.setLayout(new BoxLayout(purchaseAndSellPanel, BoxLayout.Y_AXIS));
        companyImage = new JLabel(new ImageIcon("./data/shibe.png"));
        companyImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        companyImage = new JLabel(IMAGES.get("Shibe Inc."));
        companyImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        purchaseAndSellPanel.add(createPurchaseAndSellInputPanel(purchase));
        purchaseAndSellPanel.add(companyImage);
    }

    // EFFECTS: creates the input textfield, label and button for purchasing shares
    private JPanel createPurchaseAndSellInputPanel(boolean purchase) {
        JPanel inputPanel = new JPanel();
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));

        JComboBox companiesBox = new JComboBox(COMPANIES); // TODO: add action listener
        companiesBox.addActionListener(this);

        JTextField inputField = new JTextField();
        inputField.setColumns(3);

        JLabel stockNumQuestion = new JLabel();
        JButton inputBtn = new JButton();

        if (purchase) { // TODO: add action listener
            stockNumQuestion.setText("Number of Shares to Purchase: ");
            inputBtn.setText("BUY");
        } else {
            stockNumQuestion.setText("Number of Shares to Sell: ");
            inputBtn.setText("SELL");
        }

        inputPanel.add(companiesBox);
        inputPanel.add(stockNumQuestion);
        inputPanel.add(inputField);
        inputPanel.add(inputBtn);

        return inputPanel;
    }

    // MODIFIES: this
    // EFFECTS: TODO
    private void createAllStockDetailsPanel() {
        allStockDetailsPanel = new JPanel();
        GridLayout stockDetailsLayout = new GridLayout(3, 2);
        stockDetailsLayout.setHgap(50);
        stockDetailsLayout.setVgap(10);
        allStockDetailsPanel.setLayout(stockDetailsLayout);

        for (String s: COMPANIES) {
            JLabel stockdetails = new JLabel(stockToString(s));
            stockdetails.setFont(new Font("", Font.PLAIN, 20));
            allStockDetailsPanel.add(stockdetails);
        }
    }


    // MODIFIES: this
    // EFFECTS: creates JPanel showcasing the stock details of a company
    private void createStockDetailsPanel(String name) {
        stockDetailPanel = new JPanel();
        JLabel stockDetails = new JLabel(stockToString(name));
        stockDetails.setFont(new Font("", Font.PLAIN, 20));
        stockDetailPanel.add(stockDetails);
    }

    // EFFECTS: returns stock details as a String
    private String stockToString(String name) {
        Stock stock = stockMarket.lookUpStock(name);
        DecimalFormat df = new DecimalFormat("0.##");

        return "<html><u>" + name + "</u><br>Current Value: $" + df.format(stock.getCurrentValue()) + "<br>Bid: $"
                + df.format(stock.getBidPrice()) + "<br>Ask: $" + df.format(stock.getAskPrice()) + "<br>Spread: $"
                + df.format(stock.getAskPrice() - stock.getBidPrice()) + "<br>Change Percentage: $"
                + df.format(stock.getPercentChange());
    }

    // MODIFIES: this
    // EFFECTS: sets background image of the window
    private void setBackground(String fileName) {
        background = new JLabel(new ImageIcon(fileName));
        background.setLayout(new FlowLayout());
        frame.add(background);
    }

    // MODIFIES: this
    // EFFECTS: creates panel showcasing balance in portfolio
    private void createBalancePanel() {
        DecimalFormat df = new DecimalFormat("0.##");
        balancePanel = new JPanel();
        JLabel balanceLabel = new JLabel("$" + df.format(portfolio.getBalance()));
        balanceLabel.setFont(new Font("", Font.PLAIN, 100));
        balancePanel.add(balanceLabel);
    }

    // MODIFIES: this
    // EFFECTS: if portfolio is empty, prints out "You currently don't won any stocks."
    //          else prints out info of stocks owned, i.e., the name and number of shares owned
    private void createPortfolioPanel() {
        portfolioPanel = new JPanel();
        portfolioPanel.setLayout(new BoxLayout(portfolioPanel, BoxLayout.Y_AXIS));

        ArrayList<Stock> stocksOwned = portfolio.getStocksOwned();
        String stockInfo = "";
        JLabel stockOwnedInfoLabel = new JLabel();
        stockOwnedInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (stocksOwned.isEmpty()) {
            stockInfo = "You currently don't own any stocks.";
            stockOwnedInfoLabel.setFont(new Font("", Font.PLAIN, 35));
        } else {
            for (Stock s: stocksOwned) {
                stockInfo = stockInfo + (s.getName() + ", " + s.getSharesPurchased() + ", "
                        + (s.getSharesPurchased() * s.getCurrentValue()) + "<br>");
            }
            stockOwnedInfoLabel.setFont(new Font("", Font.PLAIN, 20));
        }

        stockOwnedInfoLabel.setText(stockInfo);
        portfolioPanel.add(stockOwnedInfoLabel);
        portfolioPanel.add(Box.createVerticalStrut(10));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JComboBox) {
            JComboBox cb = (JComboBox)e.getSource();
            String companyName = (String)cb.getSelectedItem();
            companyImage.setIcon(IMAGES.get(companyName));
        }

    }

    public static void main(String[] args) {
        new StockMarketGUI();
    }

}
