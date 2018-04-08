# Project Description and Documentation  

This repository is used for publication of additional content of the LightJason project

## Usage & Tools

* [Hugo](https://gohugo.io/)
* [Pandoc-Citeproc](http://pandoc.org/)
* [SVGO](https://github.com/svg/svgo)

### Bibliographie

The bibliographie is stored under ```static/references.bib``` as an UTF-8 encoded [Bibtex](http://www.bibtex.org/) file, convert after changes the Bibtex file into a [BibJSON](http://okfnlabs.org/bibjson/) file with

```
pandoc-citeproc --bib2json static/references.bib > static/references.json
```

#### Additional Feature

* if you add a key with name ```URL``` to the Bibtex entry the URL can point to of the PDF file, and will create a download link on the publication list
* if you add a key with name ```Note``` you can reference a comment file e.g. the key-value named ```foo``` a file under ```content/publications/foo.md``` must existst with any additional comments of the reference

### Deployment

The deployment will run automatically on a ```git push``` to the ```developing```-Branch

### Prim.js configuration

For code hightlighting the JavaScript component [Prism.js](http://prismjs.com/) is used with the following definition:

| Languages                                            | Plugins                                         |
| ---------------------------------------------------- | ----------------------------------------------- |
| Markup, C-like, Bash, C, Git, Java, Prolog, Yaml | [Line Hightlight](http://prismjs.com/plugins/line-highlight/), [Line Number](http://prismjs.com/plugins/line-numbers/), [Command Line](http://prismjs.com/plugins/command-line/), Show Language, Toolbar, Copy to Clipboard |


## Content ToDo's

* Maven to Eclipse & IntelliJ Web-Video (importing projects into IDE)
* ASL Tutorial (Syntax & Semantic Example) with some tricks
* Tutorial Agent-Configuration & -Generatoren 
* Agent-Tutorial (Workflow from an idea to code)
	1. Problem description
	2. Problem splitting into distributed and task-orientated solving
	3. Agent-code developing
	4. Java-code developing - creating a logic-based event-handler with -listener
* Choice-Behaviour
	1. Fitness proportionate selection (Linear & Gibbs-Boltzmann)
	2. CES-function
	4. PCA
	4. Plan execution based on the data
* Simulation-Tutorial
	1. LibGDX with sprites & tilemaps
	2. scenario configuration with YAML
	3. Grid / Graph structure
	4. Perceiving environment
	5. Interaction between agents
* REST-API
	1. Java Webserver (here Jetty)
	2. Servlet Structure
	3. Agent-Inspector 
	4. Agent-Access via REST
	5. UI with jQuery / Angular.JS
* Video-Screencast
	* import IntelliJ pom.xml 
	* import Eclipse pom.xml
