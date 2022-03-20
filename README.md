# Acmerouter

A demo driver/destination routing Android app that attempts to demonstrates how to write architected code, while also
incorporating up-to-date Android libraries.

###Architecture

This project uses a minimal version of the **Clean Architecture** where there is clear separation between the UI, business logic,
and data access.

Domain provides answers to questions that the app has (**use cases**). For example, for this app, we need a to find a destination for a given
driver, so the question is **GetDestinationForDriver**.

The data layer holds all the logic that give access to data that the domain needs in order to run use cases. It
controls where data is coming from, e.g. cache vs remote etc, with the goal of providing a single place for the domain
to access data from.

The UI layer is where we let the user ask and then get answers that the app supports. It is responsible for calling
the right use cases based on user input. This layer is further broken down into ViewModels and Views where ViewModels
are responsible for reacting to changes that initiates in the View, in short, the ViewModel invokes use cases on behalf
of the View.

###Directory Structure

<pre>
├── <span style="color:lightGreen">di</span> contains the machenics of dependency injections of the app
├── <span style="color:lightGreen">entity</span> business objects, e.g Driver, Destination along with business logic
├── <span style="color:lightGreen">repo</span> access abstraction for data models needed to serve the app starting from the domain going down to the UI
│   ├── <span style="color:lightGreen">destination</span> contains definition, and implementation for the Destination data object's repo
│   │   └── <span style="color:lightGreen">source</span> contains a local and remote source for use as part of the repo implementation
│   └── <span style="color:lightGreen">driver</span> contains definition, and implementation for the Driver data object's repo
│       └── <span style="color:lightGreen">source</span> contains a local and remote source for use as part of the repo implementation
├── <span style="color:lightGreen">ui</span> contains all UI specific logic
│   ├── <span style="color:lightGreen">common</span> contains reusable UI code
│   ├── <span style="color:lightGreen">destination</span>
│   └── <span style="color:lightGreen">drivers</span>
└── <span style="color:lightGreen">usecase</span>
</pre>
