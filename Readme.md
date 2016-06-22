##Mocking framework comparisons

This repo contains POCs to evaluate http request response mocking frameworks based on following requirements.


| Criteria/Tools 	| GET Request 	| POST Request 	| Form Post with redirection 	| REST Based call [JSON] 	| SOAP API Call 	| Callbacks 	|
|----------------	|:-----------:	|-------------:	|----------------------------	|------------------------	|---------------	|-----------	|
| Mocky.io       	|             	|              	|                            	|                        	|               	|           	|
| Mockserver     	|      Y      	|       Y      	|              Y             	|            Y           	|               	|           	|
| wiremock       	|             	|              	|                            	|                        	|               	|           	|


* Should be able to assess request and response object for GET & POST.