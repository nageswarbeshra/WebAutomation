
Feature: Applicaation Login
Background:
Given Setup the entries in database
When Launch the browser from config variables
And hit the page url of banking site


@RegressionTest
Scenario: Adminn Page default login

Given User is on the Netbanking landing page
When User login into application with "admin" and password  "1234"
Then Home Page is displayed
And Cards are displayed


#resusable

@SmokeTest
Scenario Outline: Admin application Page default login

Given User is on the Netbanking landing page
When User login into application with "<Username>" and password  "<Password>"
Then Home Page is displayed
And Cards are displayed

Examples:
| Username | Password |
| debit	   | hello123 |
| credit   | hi2345   |


@NobileTest
Scenario: Adminn Page default sign up

Given User is on the practice landing page
When User sing up into application
| Nages	   |
| Beshra   |
| test@gmail.com   |
| 888235687909 |

Then Home Page is displayed
And Cards are displayed