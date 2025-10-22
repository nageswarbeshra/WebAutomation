
Feature: Search and place the order for products

Scenario: Search Experience for product search in both home and Offers page

Given User is on GreeenCart Landing page
When User search with Shortname "Tom" and extracted actual name of product 
Then user search for same shortname in offers page to check if the product exist

