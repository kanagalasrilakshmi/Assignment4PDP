# Assignment4PDP
# This Stocks Program allows the User to :
# 1. Create Rigid stock portfolios.
# 2. View created Rigid stock portfolios composition.
# 3. View value of Rigid portfolio on a particular date.
# 4. Create Flexible portfolios.
# 5. Modify the Flexible portfolios.
# 6. Get the value of the portfolio on a particular date.
# 7. Get the cost basis of the portfolio by a particular date.
# 8. Make a sell and purchase of a portfolio on a particular date.
# 9. Get the composition of the portfolio.

# DESIGN:
# Controller: The Controller of the program is implemented in the ControllerImplTest which has a function goStocks to run the complete program.
# Switch cases are written in the control for the user to maneuver to different sections. The levels are given in detail further in this documentation.
# model/Portfolio: All the models are independent, fully decoupled and perform only one operation each which is called by the controller.
# View: View does all the System.out operations in the program and implements no other methods.
# MVCStocks: Contains the main runner for the program.
# Step By Step rundown on commands and their operations.

# MAIN MENU:
# Provides the user with the following options:
# F : Creating, modifying and viewing Flexible Portfolios.
# R : Creating and Viewing Rigid Portfolios.
# Q : Quit Program
PROMPT invalid if anything other than above is entered

# 1. If the user chooses R he can create Rigid portfolios.
# 2. If the user chooses F he can create Flexible portfolios.

# Rigid Portfolios:

# RIGID PORTFOLIO MENU:
# C : Create Portfolio (Two level Y and S to add stocks and save portfolio respectively)
# V : View Portfolio (Two levels D and P for view value or composition respectively)
# Q : Quit Program
# Create Portfolio (C) :
# 1. Set root directory: uses valid route from user | checks if invalid directory and suggests default route and creates one, or lets user try again
# Valid USER PATH is one with no spaces,no null, no invalid characters or digits as input and should not exceed 25 length. If an invalid directory route is given the user is asked to try again or to continue with default route "/Users/Desktop/PortfolioBucket" which is created if not already existing.
# PROMPT (Y/S) for user to add stocks to the portfolio created or save portfolio.
# PROMPT If a letter other than Y or S is given then User is prompted to add Y or S only.
# 2. Add stock to Portfolio (Y):
# Checks if tickr is a valid and prompts user to input again if not.
# VALID TICKR is one that exists in the NASDAQ base list which we have in Resources.
# Checks if number of stocks is valid and prompts user to input again if not.
# VALID NUMBER is an integer
# 3. Save Portfolio (S):
# Saves portfolio in the root directory.
# PROMPT if no tickrs and number of stocks have been added yet to that particular portfolio. Asks user to add.
# Takes user back to main menu.

# View Portfolio "V":
#1. Shows list of available portfolios in directory or prompts user to create one.
# All the portfolios in the given valid user path(set by user) or in the default path saved in .txt format (since all our portfolios saved in .txt format) are listed down.
# User is given choice to enter the name of the files from the listed files only.
# PROMPT If he enters a file not in the list or enters a wrong name then he prompted to enter the portfolio names from the displayed list of files only.
2. Show Composition (P):
# Once the user enters a right portfolio name, the tickr symbols and num stocks of that portfolio is displayed.
# This feature works perfectly fine.
3. Show Value (D):
# Users is prompted to input date.
# If the user enters a valid format, and the date falls on saturday or sunday then friday date's stock value is used to calculate the value of the porfolio.This feature works fine.
# If the user gives a future date, then the user is notified saying that the date is in future and stock price value for future cannot be available. This feature works fine.
# If the user gives today's date and that falls on mon to friday : If time is before 9:30 am the user is notified with a message saying that stock market is yet to be opened so no price is available. Th the time is after 9:30 am and before midnight 12 the closing price for that day is taken for calculation of portfolio value. This feature works fine.

# EXIT USING "Q": Works correctly, When entered user exists the program.Works perfectly.

# Flexible Portfolios:

# FLEXIBLE PORTFOLIO MENU:
# C: To create a new Portfolio.
# M: To modify an existing Portfolio.
# V: View existing Portfolio.
# Q: Quit the program.
# B: Get Bar graph of the program.

# Create Portfolio (C) :
# 1. Set root directory: uses valid route from user | checks if invalid directory and suggests default route and creates one, or lets user try again
# Valid USER PATH is one with no spaces,no null, no invalid characters or digits as input and should not exceed 25 length. If an invalid directory route is given the user is asked to try again or to continue with default route "/Users/Desktop/PortfolioBucket" which is created if not already existing.
# PROMPT (Y/S) for user to purchase stocks and add to the portfolio created or save portfolio.
# PROMPT If a letter other than Y or S is given then User is prompted to add Y or S only.
# 2. Add stock to Portfolio (Y):
# Checks if tickr is a valid and prompts user to input again if not.
# VALID TICKR is one that exists in the NASDAQ base list which we have in Resources.
# Checks if number of stocks is valid and prompts user to input again if not.
# VALID NUMBER is an integer
# Checks if commision fees is a positive integer or float value.
# Checks if the date on which the user tries to make the purchase is in right format.(Not a future date, or date before stock market opens today).
# 3. Save Portfolio (S):
# Saves portfolio in the root directory.
# Empty portfolio can be created.
# Takes user back to main menu.

# View Portfolio "V":
#1. Shows list of available portfolios in directory or prompts user to create one.
# All the portfolios in the given valid user path(set by user) or in the default path saved in .json format (since all our portfolios saved in .json format) are listed down.
# User is given choice to enter the name of the files from the listed files only.
# PROMPT If he enters a file not in the list or enters a wrong name then he prompted to enter the portfolio names from the displayed list of files only.
2. Show Composition (P):
# Once the user enters a right portfolio name, the tickr symbols,num stocks purchased or sold, Date on which purchase or sale is made,stock price for which a stock is purchased or sold, of that portfolio is displayed.
# This feature works perfectly fine.
3. Show Value (D):
# User is prompted to input date.
# If the user enters a valid format, and the date falls on saturday or sunday then friday date's stock value is used to calculate the value of the portfolio.This feature works fine.
# If the user gives a future date, then the user is notified saying that the date is in future and stock price value for future cannot be available. This feature works fine.
# If the user gives today's date and that falls on mon to friday : If time is before 9:30 am the user is notified with a message saying that stock market is yet to be opened so no price is available. The the time is after 9:30 am and before midnight 12 the closing price for that day is taken for calculation of portfolio value. This feature works fine.
# If a date when the stock was not even present was given still it takes arbitary values which needs to be taken care of.
# If the user enters a date before the first purchase made in the portfolio then it returns a value of 0.
4. Show Cost Basis (I):
# User is prompted to input date.
# If the user enters a valid format, and the date falls on saturday or sunday then friday date's stock value is used to calculate the value of the porfolio.This feature works fine.
# If the user gives a future date, then the user is notified saying that the date is in future and stock price value for future cannot be available. This feature works fine.
# If the user gives today's date and that falls on mon to friday : If time is before 9:30 am the user is notified with a message saying that stock market is yet to be opened so no price is available. Th the time is after 9:30 am and before midnight 12 the closing price for that day is taken for calculation of portfolio value. This feature works fine.
# API Key has a restriction on number of calls to be made. So, a work around was done inorder to overcome this problem. "tickrData.txt" has prices too for every tickr symbol in the porfolio. After an api call if there is no value returned due to number of calls made over an api has exceeded it will use the prices from "tickrData.txt" file for calculating the value.
# This feature does not completely work fine since the stock price values taken here belong to some date that is not specified by the user.
# Instead of making immediate api calls, a timer should be setup that limits the api calls at a given time.
# If a date when the stock was not even present was given still it takes arbitary values which needs to be taken care of.
# If the user enters a date before the first purchase made in the portfolio then it returns a value of 0.
# Cost basis of a portfolio is computed by calculating the cost spent in making a transaction(sell/purchase) for every tickr symbol present in the portfolio.
# For purchase - money spent while buying stocks are added along with commission fees.
# For sell - commission fees paid while selling stocks is included.
# This feature works perfectly fine.

# Modify Portfolio "M":
# User is prompted if he wants to sell or purchase stocks.
# If user wants to sell stocks on a particular date for a particular tickr symbol, if the tickr symbol exists in the portfolio is checked initially, if not present user is prompted to enter valid tickr symbol that is in portfolio.
# number of stocks till that date are checked. If its number is less than the stocks to be sold then user prompted that this transaction cannot be made.
# If user wants to purchase stocks on a particular date for a particular tickr symbol, tickr symbol is checked against the NASDAQ symbols. If not valid user is prompted to add a valid tickr symbol.
# If user gives a negative commission fees then prompted to give wither float or integer values only.
# if the user gives the date of purchase in wrong format or a future date or a today's date before stock market opens(9:30 am) then he is prompted to give a valid date.



# Performance of the Portfolio "B":
# User is prompted to give range of two dates between which you want to get the performance of the portfolio.
# if first date given by user is greater than the second date given by the user then user is prompted to enter the dates again.
# if the difference in range is less than 5 days then portfolio performance is not computed.
# if the difference in dates is more than or equal to 5 days and less than or equal to 30 days then performance for every date in this range is displayed in format - eg: "2021-11-11, 2021-11..."
# if the difference in dates is more than 30 days and less than or equal to 150 then performance for every date in 5 days interval is displayed in format - eg: "2021-11-11, 2021-11-16,...."
# if the difference in dates is more than 150 days and less than or equal to 900 days then performance for every month in this range is displayed in format - eg: "JAN 2020, FEB 2020 .."
# if the difference in dates is more than 900 and less than or equal to 1800 days then performance for every three months in this range is displayed in format - eg:"JAN 2020, MAR 2020, JUN 2020..."
# if the difference in dates is more than 1800 days then performance is computed for every year in this range is displayed in format - eg:"2016,2017..."
# Scale is computed by doing the following :
# Range of values (Max value of portfolio - Min Value of Portfolio) of the portfolio is calculated and divided 50(To not exceed 50 asterisks per line).
# Min value of the portfolio is subtracted from the portfolio values in that range, then they are divided by the scale obtained previously.
# These values are used for plotting performance of the portfolio.





# EXIT USING "Q": Works correctly, When entered user exists the program.Works perfectly.

# NOTE : (Always give date in YYYY-MM-DD format only, however if user gives date in other format he will be prompted to give it in right format)
