---
title: "Tutorial: Environment"
jsonld: ["techarticle", "course"]
gitter: "tutorials"
previous :
    url: "/tutorials/communication"
    text: "Communication"  
next:
    url: "/about"
    text: "About"          
---


The LightJason system architecture does not require any environment, but you can easily write your own. Based on the _asynchronous and parallel_ execution model in LightJason you have to create thread-safe data structures for your environment to avoid any [race condition](https://en.wikipedia.org/wiki/Race_condition).
__Keep in mind that all calls of the environment are done in parallel and asynchronously, so your environment must handle these accesses correctly.__

{{< toc >}}

## Previous Knowledge

The tutorial consists of three steps:

1. create the basic agent structure based on the [AgentSpeak 15min](/tutorials/agentspeak-in-fifteen-minutes/), [Triggering](/tutorials/trigger/) and [Actions](/tutorials/actions/) tutorials
2. we use the [object-actions (internal actions)](/tutorials/actions/#object-actions-internal-actions) to pass the calls from the agent to the environment
3. we create a _thread-safe_ environment which can execute the _object-actions_ from the agent.


## Environment

For this example we use a small structure within which the agents should change their positions. The agents can move from one cell to the left or right, but the agents can only move if the cell is free. The number of cells is $1.5 \cdot \text{number of agents}$, so there are guaranteed free cells. The agent position will be set at random on initialisation.

{{< img src="/images/environment.svg" width="35%" >}}

In the environment, we need two structures for storing, both structures must be thread-safe:

* a storage for the agents (the cell definition) {{< linelink "" "env" "13" >}}
* a map to store agent positions for read access {{< linelink "" "env" "15" >}}

For execution we need two methods:

* a method for initial set of the agent {{< linelink "" "env" "35-49" >}}
* the method for moving, which is used for the agent action {{< linelink "" "env" "51-83" >}}

With a trigger {{< linelink "" "env" "65-72" >}} we notify all other agents if an agent changes the position {{< linelink "" "env" "78-79" >}}. If the agent cannot move because the cell is not empty, we throw an exception on {{< linelink "" "env" "82" >}} and this will fail the agent action.

<!-- htmlmin:ignore -->
{{< githubsource user="LightJason" repo="Examples" file="src/main/java/myagentproject/CEnvironment.java" lang="java" branch="tutorial-environment" id="env" >}}
<!-- htmlmin:ignore -->



## Agent with environment actions

The agent class must define the actions which passed the call to the environment. Based on this definition the action can be called inside the agent script.

### Agent class

In this example we implement the action as [object-actions (internal actions)](/tutorials/actions/#object-actions-internal-actions) on {{< linelink "" "agentclass" "21-26" >}}. The method name can be arbitrarily chosen, so the annotation defines the action name. Arguments can be passed with the native Java type inside the method.
The method passes the data to the method of the environment object {{< linelink "" "agentclass" "25" >}}. The environment object is set by the constructor and stored inside the agent object {{< linelink "" "agentclass" "13, 15, 18" >}}. All agents references the same environment, because in Java all inheritance of objects are passed as references.

<!-- htmlmin:ignore -->
{{< githubsource id="agentclass" user="LightJason" repo="Examples" file="src/main/java/myagentproject/MyAgent.java" lang="java" branch="tutorial-environment" >}}
<!-- htmlmin:ignore -->

### Agent AgentSpeak(L++) Script

The agent script can use the action ```env/move``` at {{< linelink "" "asl" "10" >}}. The parameter is the new position, but this can fail, so the whole can fail also. Here we encapsulate the action with a plan to handle failure execution. The failure execution starts with the plan {{< linelink "" "asl" "15-20" >}}. Each of the _movement plans_ calls itself to create an _infinity loop_ of moving. In parallel the plan {{< linelink "" "asl" "21-23" >}} will be executed if another agents have been moved.

<!-- htmlmin:ignore -->
{{< githubsource user="LightJason" repo="Examples" file="agent_environment.asl" lang="agentspeak" branch="tutorial-environment" id="asl" >}}
<!-- htmlmin:ignore -->



## Agent generator with environment

The agent generator is structured for a flexible environment, so with the constructor the environment object is set into {{< linelink "" "generator" "12, 14, 31" >}}

<!-- htmlmin:ignore -->
{{< githubsource user="LightJason" repo="Examples" file="src/main/java/myagentproject/MyAgentGenerator.java" lang="java" branch="tutorial-environment" id="generator" >}}
<!-- htmlmin:ignore -->

### Variable Builder

For creating fast access to data of the environment (size) and agent name (hashcode) we use a variable builder that gets the environment also as reference {{< linelink "" "variablebuilder" "13, 15, 17" >}}. The _generate_ method creates constants for each plan {{< linelink "" "variablebuilder" "20-27" >}}. Regarding the environment data, {{< linelink "" "variablebuilder"  "25" >}} creates a variable with the size of the environment

<!-- htmlmin:ignore -->
{{< githubsource user="LightJason" repo="Examples" file="src/main/java/myagentproject/CVariableBuilder.java" lang="java" branch="tutorial-environment" id="variablebuilder" >}}
<!-- htmlmin:ignore -->


## Reference Solution

If you struggled at some point or wish to obtain our exemplary solution with code documentation of this tutorial, you can download the archive {{< githubzip user="LightJason" repo="Examples" branch="tutorial-environment" >}} containing the source code and a executable {{< githubdownload user="LightJason" repo="Examples" branch="jar-tutorial-environment" file="myagentapp-1.0-SNAPSHOT.jar" text="Jar file" >}}.


We run the example with 5 agents and 5 cycles (different runs returns different outputs)

```commandline
agent   2.003.087.617   is on position   6   move to    2
agent   544.479.674   is on position   2   move to    3
agent   793.503.139   is on position   5   move to    1
agent   2.135.449.562   is on position   3   move to    4
agent   1.713.875.358   get information that other agent has moved from   3   to   4
agent   1.713.875.358   get information that other agent has moved from   5   to   1
agent   1.713.875.358   is on position   0   move to    1
agent   2.135.449.562   get information that other agent has moved from   5   to   1
agent   2.135.449.562   is on position   4   move to    0
agent   544.479.674   get information that other agent has moved from   3   to   4
agent   544.479.674   get information that other agent has moved from   5   to   1
agent   544.479.674   cannot move from   2   to    3   try to move to   1.0
agent   2.003.087.617   get information that other agent has moved from   3   to   4
agent   2.003.087.617   get information that other agent has moved from   5   to   1
agent   2.003.087.617   cannot move from   6   to    2   try to move to   5.0
agent   793.503.139   is on position   1   move to    2
agent   793.503.139   get information that other agent has moved from   3   to   4
agent   1.713.875.358   cannot move from   0   to    1   try to move to   4.0
agent   2.003.087.617   is on position   6   move to    5.0
agent   544.479.674   is on position   2   move to    1.0
agent   2.135.449.562   cannot move from   4   to    0   try to move to   3.0
agent   793.503.139   cannot move from   1   to    2   try to move to   0.0
agent   1.713.875.358   is on position   0   move to    4.0
agent   1.713.875.358   get information that other agent has moved from   6   to   5
agent   2.003.087.617   is on position   5   move to    1
agent   2.135.449.562   get information that other agent has moved from   6   to   5
agent   2.135.449.562   is on position   4   move to    3.0
agent   544.479.674   get information that other agent has moved from   6   to   5
agent   544.479.674   cannot move from   2   to    1.0   try to move to   1.0
agent   793.503.139   is on position   1   move to    0.0
agent   793.503.139   get information that other agent has moved from   6   to   5
agent   1.713.875.358   cannot move from   0   to    4.0   try to move to   4.0
agent   1.713.875.358   get information that other agent has moved from   4   to   3
agent   2.003.087.617   get information that other agent has moved from   4   to   3
agent   2.135.449.562   is on position   3   move to    4
agent   2.003.087.617   cannot move from   5   to    1   try to move to   4.0
agent   544.479.674   get information that other agent has moved from   4   to   3
agent   544.479.674   is on position   2   move to    1.0
agent   793.503.139   get information that other agent has moved from   4   to   3
agent   793.503.139   cannot move from   1   to    0.0   try to move to   0.0
agent   1.713.875.358   get information that other agent has moved from   3   to   4
agent   1.713.875.358   is on position   0   move to    4.0
```
