# Assignment4PDP
# This Stocks Program allows the User to :
# 1. Create stock portfolios
# 2. View created portfolios composition.
# 3. View value of portfolio on a particular date.

# DESIGN:
# Controller: The Controller of the program is implemented in the ControllerImplTest which has a function goStocks to run the complete program.
# Switch cases are written in the control for the user to maneuver to different sections. The levels are given in detail further in this documentation.
# model/Portfolio: All the models are independent, fully decoupled and perform only one operation each which is called by the controller.
# View: View does all the System.out operations in the program and implements no other methods.
# MVCStocks: Contains the main runner for the program.
# Step By Step rundown on commands and their operations.

# MAIN MENU:
# Provides the user with the following options:
# C : Create Portfolio (Two level Y and S to add stocks and save portfolio respectively)
# V : View Portfolio (Two levels D and P for view value or composition respectively)
# Q : Quit Program
PROMPT invalid if anything other than above is entered

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
# API Key has a restriction on number of calls to be made. So, a work around was done inorder to overcome this problem. "tickrData.txt" has prices too for every tickr symbol in the porfolio. After an api call if there is no value returned due to number of calls made over an api has exceeded it will use the prices from "tickrData.txt" file for calculating the value.
# This feature does not completely work fine since the stock price values taken here belong to some date that is not specified by the user.
# Instead of making immediate api calls, a timer should be setup that limits the api calls at a given time.
# If a date when the stock was not even present was given still it takes arbitary values which needs to be taken care of.


# EXIT USING "Q": Works correctly, When entered user exists the program.Works perfectly.
 
