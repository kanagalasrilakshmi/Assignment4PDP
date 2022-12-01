# Assignment 6: Stocks (Part 3)
# This Stocks Program has the following new features added :
# 1. Dollar-cost averaging
# 1.1. Allows users to now invest a fixed amount into an existing portfolio containing multiple stocks, using a specified weight for each stock in the portfolio.
# 1.2. Start-to-finish" dollar-cost averaging as a single operation and persisting any given strategy such that it can be retrieved to compare strategies in the future
# 2. Graphical User Interface
# 3. All other previous features and controllers co-exist and don't disturb the functionality of new features


# DESIGN:
# Controller: The Controller of the program is implemented in the ControllerGUI which extends a common controller interface. which has a function goStocks to run the complete program.
# The new GUI is used by the user to maneuver to different sections. The levels are given in detail further in this documentation.
# model/Portfolio: The Rigid/Flexible portfolios extend the Portfolio interface.
# The model for this assignment is written in the PortfolioNewStrategy class that extends the previous model implementation flexiblePortfolio class and implements a different interface called PortfolioStrategy.
# View: View does all the System.out operations in the program and implements no other methods.
# The view for the assignment is GUI based and is written in the GUIViewImpl that implements the common view interface. Every panel has its own class and interface which is present in the panels' directory.
# Different panels for cost basis, creation, dollar strategies, portfolio modification, retrieve and quit are created.
# The API is put in a separate class that implements the API interface.
# The file operations are put in a separate class that implements the FileOperation interface
# MVCStocks: Contains the main runner for the program.
# Step By Step rundown of how it works and its operations.

# MAIN MENU (GUI):
# 1. Set root directory: uses valid route from user | checks if the invalid directory and suggest default route and creates one, or lets the user try again
# Valid USER PATH is one with no spaces, no null, and no invalid characters or digits as input and should not exceed 25 in length. If an invalid directory route is given the user is asked to try again or to continue with the default route "/Users/Desktop/PortfolioBucket" which is created if not already existing.
# 2. Create a new portfolio
# 3. Get Cost basis
# 4. Get Value of the portfolio
# 5. Retrieve portfolio
# 6. Calculate dollar cost averaging
# 7. Create dollar cost averaging
# 8. Quit the program

# STRATEGIES:
# All strategies are stored in a strategy lookup json file that is common for all the portfolios in the portfolio bucket root.
# Each strategy can be fetched anytime using the pf name and strategy name which are keys in the json file.
# Multiple strategies for the same pf are also allowed and input strategy is recorded against that strategy name.
# If end date is ONGOING records will persisted for the next 365 days in the portfolio if in the past.

FEATURE 1:
# 1. Dollar-cost averaging:
# 1.1 Invest a fixed amount on a date:
#     - Shows a list of available portfolios in the directory or prompts the user to create one.
#     - Users enter the portfolio in which he wants to make the investment.
#     - Get the total investment the user wants to make.
#     - Enter all the tickr symbols in which they want to invest.
#     - Enter the weights to determine what proportion they want to invest.
#     - Enter the start and end dates and the frequency at which they want to invest in these tickrs.

# 1.2 Start-to-finish:
#     - Allows users to create a portfolio.
#     - Get the total investment the user wants to make.
#     - Enter all the tickr symbols in which they want to invest.
#     - Enter the weights to determine what proportion they want to invest.
#     - Enter the start and end dates and the frequency at which they want to invest in these tickrs.
#     - Enter the name they want to give this particular strategy.
#     - All the strategies will be saved and in case of giving the same name for two strategies the previous one will be overwritten.

# FEATURE 2:
# New User Interface
# The 'X' button at the top left has to be used to close any panel.
# All the input fields are validated sequentially and invalid input prompts are displayed whenever necessary while trying to update or persist something.

# VALIDATIONS:
# All the portfolios in the given valid user path(set by the user) or in the default path saved in .json format (since all our portfolios saved in .json format) are listed down.
# User is given the choice to enter the name of the files from the listed files only.
# From start to finish if stocks are scheduled to be bought on Saturday or Sunday it will be done on Monday ie the next valid date
# Set root directory: uses valid route from user | checks if the invalid directory and suggest default route and creates one, or lets user try again
# Valid USER PATH is one with no spaces, no null, and no invalid characters or digits as input and should not exceed 25 lengths. If an invalid directory route is given the user is asked to try again or to continue with the default route "/Users/Desktop/PortfolioBucket" which is created if not already existing.
# Checks if tickr is valid and prompts the user to input again if not.
# VALID TICKR is one that exists in the NASDAQ base list which we have in Resources.
# Checks if the number of stocks is valid and prompts the user to input again if not.
# VALID NUMBER is a positive integer
# Checks if commission fees are a positive integer or float value.
# Checks if the date on which the user tries to make the purchase is in the right format. (Not a future date, or a date before the stock market opens today).
# Saves portfolio in the root directory.
# Empty portfolio can be created.
# Takes user back to the main menu whenever needed.

# If the user enters a date before the first purchase made in the portfolio then it returns a value of 0.
# User is prompted to input the date.
# If the user gives today's date and that falls on Mon to Friday: If the time is before 9:30 am the user is notified with a message saying that the stock market is yet to be opened so no price is available. The time is after 9:30 am and before midnight 12 the closing price for that day is taken for calculation of portfolio value.
# API Key has a restriction on the number of calls to be made. So, a workaround was done in order to overcome this problem. "tickrData.txt" has prices too for every tickr symbol in the portfolio.
# After an API call if there is no value returned due to the number of calls made over an API has exceeded it will use the prices from "tickrData.txt" file for calculating the value.
# This feature does not completely work fine since the stock price values taken here belong to some date that is not specified by the user.
# Instead of making immediate api calls, a timer should be setup that limits the api calls at a given time.
# If a date when the stock was not even present was given still it takes arbitrary values which needs to be taken care of.
# If the user enters a date before the first purchase made in the portfolio then it returns a value of 0.
# Cost basis of a portfolio is computed by calculating the cost spent in making a transaction(sell/purchase) for every tickr symbol present in the portfolio.
# For purchase - money spent while buying stocks is added along with commission fees.
# For sale - commission fees paid while selling stocks are included.
# This feature works perfectly fine.
# If the user gives a negative commission fee then prompted to give either float or integer values only.

# Performance of the Portfolio (from the previous assignment):
# User is prompted to give a range of two dates between which you want to get the performance of the portfolio.
# if the first date given by the user is greater than the second date given by the user then the user is prompted to enter the dates again.
# if the difference in range is less than 5 days then portfolio performance is not computed.
# if the difference in dates is more than or equal to 5 days and less than or equal to 30 days then performance for every date in this range is displayed in format - eg: "2021-11-11, 2021-11..."
# if the difference in dates is more than 30 days and less than or equal to 150 then performance for every date in 5 days intervals is displayed in format - eg: "2021-11-11, 2021-11-16,...."
# if the difference in dates is more than 150 days and less than or equal to 900 days then performance for every month in this range is displayed in format - eg: "JAN 2020, FEB 2020 .."
# if the difference in dates is more than 900 and less than or equal to 1800 days then performance for every three months in this range is displayed in format - eg: "JAN 2020, MAR 2020, JUN 2020..."
# if the difference in dates is more than 1800 days then performance is computed for every year in this range and is displayed in format - eg: "2016,2017..."
# Scale is computed by doing the following :
# Range of values (Max value of portfolio - Min Value of Portfolio) of the portfolio is calculated and divided 50(To not exceed 50 asterisks per line).
# Min value of the portfolio is subtracted from the portfolio values in that range, then they are divided by the scale obtained previously.
# These values are used for plotting the performance of the portfolio.

# NOTE : (Always give a date in YYYY-MM-DD format only, however, if the user gives a date in another format he will be prompted to give it in the right format) anywhere throughout the application.