# Java Ear Training
<div align="center">
<img src="https://img.shields.io/badge/Java-007396?style=flat&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring-6DB33F?logo=spring&logoColor=white&style=flat"/>
<img src="https://img.shields.io/badge/JSON-000000?style=flat&logo=json&logoColor=white" />
<img src="https://img.shields.io/badge/JavaFX-FC4C02?style=flat&logo=javafx&logoColor=white"/>
<img src="https://img.shields.io/badge/FXML-FC4C02?style=flat&logoColor=white"/>
<img src="https://img.shields.io/badge/CSS-1572B6?style=flat&logo=css3&logoColor=white"/>
</div>

## Quick Start
Download the project and run with Maven:
```bash
git clone https://github.com/scrVrdn/java-ear-training.git
cd java-ear-training
mvn javafx:run
```
On startup the, the app creates a directory in the user home directory where the app presets will be stored in JSON format ("settings.json").

## What it does
This app offers the user all they need for essential ear training
* in the main menu the user chooses between practicing interval or chord (soon) recognition
* after pressing the start button in the exercise section, the user will be presented with a random interval or chord which have to be identified by pressing one of the answer buttons. On pressing the correct answer button, the app presents immediately the next interval or chord otherwise the button is colored in red.
* the user can replay the interval or chord as many times as they like or skip it with the next button
* in the settings section (☰ button) the user can adjust all the relevant parameters and save a specific configuration as a preset
* on shutdown, the app automatically writes the saved presets to a JSON file ("settings.json")
* on startup, the app loads all presets from "settings.json" including the last used preset – in the case of an empty settings file, a default preset is provided
