# Example integration with censorREACT from SpringFramework

This simple SpringBoot application will show on how to call censorREACT Text and Image APIs from a Spring based application

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Prerequisites

This sample application is using a Gradle based build system. You should be able to run it from Android Studio or VSCode editors.

You could also run this sample project from the command line

```
# Clean 
./gradlew clean

# Build
./gradlew build

# Run
./gradlew bootRun
```

### Installing

After cloning the sample app into your local, you could run it from Android Studio or VSCode editors. You could also build and run it from the command line.

By default the sample app will be running on port 8080. You can access the sample form for calling censorREACT Text API:

```
http://localhost:8080/text
```

And for Image API:

```
http://localhost:8080/image
```

### Running Text API example:

In order to test censorREACT Text Verification API, you will need to specify your API Key. If you haven't got one, please refer to censorREACT **Gettting Started** documentation on how to create an account and generate your API Key.

https://docs.censorreact.intygrate.com/getting-started/step1/
 
Once you have your API Key available, you can proceed with your testing by providing the below details
```
# This will be your API Key
Your API Key

# This will be your Text setting profile 
Your Profile 

# This will be your text payload
Your Payload
```

### Code Snipped

The main logic logic for calling Intygrate Text API can be found TextController.java

```
// Text API verification URL
String crURL = "https://api.censorreact.intygrate.com/v1/text";

// Set content type and 'x-api-key' into Header
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_JSON);
headers.add("x-api-key", payload.getApiKey());

payload.setProfile(payload.getProfile() == "" ? "default" : payload.getProfile().trim());
payload.setText(payload.getText() == "" ? "Specify your payload here" : payload.getText().trim());

// Use Spring RestTemplate object calling the Text API
RestTemplate restTemplate = new RestTemplate();
HttpEntity<Payload> request = new HttpEntity<>(payload, headers);

Response response = new Response();
try {
  Object result = restTemplate.postForObject(crURL, request, Object.class);
  response.setData(result.toString());
} catch (Exception ex) {
  response.setData(ex.getMessage());
}

model.addAttribute("response", response);
return "text";
```

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details