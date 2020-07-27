package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.exception.InterruptOperationException;
import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.repository.BikeRepository;
import com.qualityunit.ecobike.view.ConsoleView;

import java.util.List;

public class ShowCatalogCommand implements Command {

    private List<AbstractBike> bikes;
    private int numberOfItems;
    private int page;
    private int pageSize;
    private int numberOfPages;

    public void setBikes(List<AbstractBike> bikes) { // SearchBikesCommand use it after search
        this.bikes = bikes;
    }

    @Override
    public void execute() {
        synchronized (BikeRepository.getInstance()) {
            if (bikes == null) {
                bikes = BikeRepository.getInstance().findAll();
            }

            numberOfItems = bikes.size();
            if (numberOfItems == 0) {
                bikes = null;
                ConsoleView.write("There are no items to show");
                return;
            }

            page = 1;
            if (numberOfItems > 25) {
                int pageChoice = ConsoleView.readNumberBetween("There are " + bikes.size() + " items to show." +
                        "\nHow many items do you want to see on one page?" +
                        "\n1 - 10 items" +
                        "\n2 - 15 items" +
                        "\n3 - 20 items" +
                        "\n4 - 25 items", 1, 4);

                switch (pageChoice) {
                    case 1:
                        pageSize = 10;
                        break;
                    case 2:
                        pageSize = 15;
                        break;
                    case 3:
                        pageSize = 20;
                        break;
                    case 4:
                        pageSize = 25;
                        break;
                }
                if (numberOfItems % pageSize == 0) {
                    numberOfPages = numberOfItems / pageSize;
                } else {
                    numberOfPages = numberOfItems / pageSize + 1;
                }
            } else {
                pageSize = numberOfItems;
                numberOfPages = 1;
            }
            try {
                showBikes();
            } catch (InterruptOperationException e) {
                bikes = null;
                throw new InterruptOperationException();
            }
        }
    }

    private void showBikes() {
        int firstItemOnPage = (page - 1) * pageSize;
        int lastItemOnPage = Math.min(firstItemOnPage + pageSize, numberOfItems) - 1;
        ConsoleView.write("");
        for (int i = firstItemOnPage; i <= lastItemOnPage; i++) {
            ConsoleView.write(bikes.get(i).toString());
        }
        ConsoleView.write("");

        page = ConsoleView.readNumberBetween("Current page: " + page + ". Number of pages: " + numberOfPages +
                "\nEnter number of page you want to see next or 'exit' to return to main menu", 1, numberOfPages);

        showBikes(); // until user enters "exit"
    }
}
