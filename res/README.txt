# Assignment4PDP

# WHAT WORKS!
# 1. Creates portfolios in the directory specified by the user (Invalid routes are prompted and handled).
# 2. Validates if tickr symbols are listed in NASDAQs list from the resources.
# 3. Validates format of the input number of stocks.
# 4. Checks if input date is in the past, future, present, present (but before 9:30 am), or invalid date formats and handles it.
# 5. Every stage is prompted with messages for allowed valid operations.
# 6. Lists all the portfolios created by the user and allows the user to choose from them for getting portfolio value of porfolio composition.

# WHAT !WORKS
# 1. API key time out is not handled. Need to add timeout feature to handle this.
 CURRENT WORKAROUND : If API times out data in the "tickrData.txt"(in "res" folder) is used for calculating the value of the portfolio.
# 2. Cannot fetch if the date is prior to which the tickr was listed in NASDAQ.
 CURRENT WORKAROUND : Data from the "tickrData.txt"(in "res" folder) is used for calculating the value of the portfolio.
# 3. Testing for stock value before 9:30 am could not be done since local time changes at every run.
# 4. Limiting number of stocks that can be added at once. The API is not used  to check if a large number like 100M is added.
