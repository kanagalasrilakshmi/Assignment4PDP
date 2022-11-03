# Assignment4PDP
# This Stocks Program allows the User to Create Portfolios, View Created Porfolios and Fetch the Value of the Portfolios
# Step By Step will be taking over the each features and explain what will work and needs work.
# Controller : 
# The Controller of the Program is implemented in the ControllerImplTest.
# It intially takes valid user path with no spaces,no null, no invalid characters and digits as input.
# If a user gives a path that is not ending with "/" he is asked to give the path again. This is done because if the user gives /Users/Desktop and then file name as "portfolio" then is gets append with the given path as "/Users/Desktopportfolio.txt" which does not exists. Hence it is essential to end a path with /. A condition is also added to support this feature and this works.
# If a user gives a path, that does not exist, the program checks for it the path does not exist, then the program saves it in the local desktop, a folder named Portfolio Bucket is created and files are by default saved into it. A condition is also added to support this feature and this works.
# The controllor can allow the user to create portfolio using switch case "C", View a portfolio using switch case "V" and get value of the portfolio using switch case option "D", and can exit the program using switch case "Q".
# If an invalid switch case input other than C, V, D, Q are given, it will say the user should enter correct input and prompt the user to enter again.

# EXIT PORTFOLIO USING "Q": Works correctly, When entered user exists the program.Works perfectly.

# CREATE PORTFOLIO USING "C": If a user presses C, He is allowed to enter portfolio name, A valid Tickr symbol ,Number of stocks.
 # SWTCH CASE "Y" to add entries in the portfolio, SWICTCH CASE "S" to add save portfolio when done adding.
 # If a letter other than Y or S is given then User is prompted to add only Y or S only.
  # ENTER PORTFOLIO NAME: If a name is null, length is 0 , length > 25, portfolio has spaces, portfolio has special characters, portfolio has numbers then the name is said to invalid and user is prompted to add the name again. If the portfolio name already exists(If user gives valid user path in that location or in default location), then user is prompted to give another portfolio name. This feature works completely fine.If a user tries to save the portfolio without adding a single entry then the user is asked to enter atleast one entry and portfolio must contain a single entry.
  # A VALID TICKR SYMBOL: If the user gives a valid tickr symbol then it is added to the portfolio. If the user gives a symbol that already exists the user is prompted to add another tickr symbol since it exists.The tickr symbols are validated using "tickrData.txt" file that contains some valid tickr symbols entries. This feature can be improved by including many more valid tickr symbols.
  # VALID NUMBER OF STOCKS: The user is allowed to enter only integer values. If the user enters float, double or other data type values an exception is thrown and user is prompted to add a new stock tickr symbol and price again. This feature could be made better by asking the user to only give a new value instead of prompting him to add a new value for tickr symbol and price.
 # SAVING A PORTFOLIO : If the USER presses "S" switch case then portfolio wiht given valid portfolio name and entries are saved in [portfolio name].txt file and added to the path which user has given intially(Saved there if valid path was given else it is saved in portfolio bucket only!!). This feature works perfectly fine.

# VIEW PORTFOLIO USING "V": If a user uses V, all the portfolios in the given valid user path(if exists) or in the default path saved in .txt format (since all our portfolios saved in .txt format) are listed down. User is given choice to enter the name of the files from the listed files only. If he enters a file not in the list or enters a wrong name then he prompted to enter the portfolio names from the displayed list of files only. Once the user enters a right portfolio name, the values he entered, tickr symbols and num stocks while createing portfolio are displayed. If there are no portfolios in the file then a message specifying that no files exists displays. This feature works perfectly fine.



 
