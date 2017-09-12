---
title: "Basic Knowledge: Variables"
jsonld: ["techarticle"]
gitter: "knowledge base"
previous :
    url: "/knowledgebase/literals"
    text: "Basic Knowledge: Literals"
next :
    url: "/knowledgebase/plansandrules"
    text: "Basic Knowledge: Plans and Logic Rules"
---

Variables are special terms to store data during runtime

<style>
svg.railroad-diagram path {
    stroke-width: 2;
    stroke: grey;
    fill: rgba(0,0,0,0);
}
svg.railroad-diagram text {
    font: bold 14px monospace;
    text-anchor: middle;
}
svg.railroad-diagram rect {
    stroke-width: 3;
    stroke: black;
}

svg.railroad-diagram .non-terminal {
    fill: #14811a;
}
code.remark-inline-code {
    color: #8C1C00;
}
</style>
<svg height=300px class="railroad-diagram centering" viewBox="0 0 520 199" id="svg_70d97cd67aba0712a4ac127a7307ad12"><path d="M20 30v20m10-20v20M20 40h20.5m-.5 0h10m0 0h20" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M70 29h140v22H70z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="https://lightjason.github.io/AgentSpeak/rrd-output/html/org/lightjason/agentspeak/grammar/Agent.g4/index.htm#c857e06a9c6dcbcfd625e4859380c98e"><text x="140" y="44">UPPERCASELETTER</text></a></g><path d="M210 40h20M50 40a10 10 0 0 1 10 10v10a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M70 70h20m100 0h20M90 59h100v22H90z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="https://lightjason.github.io/AgentSpeak/rrd-output/html/org/lightjason/agentspeak/grammar/Agent.g4/index.htm#a058c5dc734e54ff3d93b96acac339f4"><text x="140" y="74">UNDERSCORE</text></a></g><path d="M210 70a10 10 0 0 0 10-10V50a10 10 0 0 1 10-10m0 0a10 10 0 0 0 10-10 10 10 0 0 1 10-10m0 0h200m0 0a10 10 0 0 1 10 10 10 10 0 0 0 10 10m-240 0h20m0 0h10m0 0h20" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M280 29h140v22H280z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="https://lightjason.github.io/AgentSpeak/rrd-output/html/org/lightjason/agentspeak/grammar/Agent.g4/index.htm#28e746830337961c5de40b87c99980a6"><text x="350" y="44">LOWERCASELETTER</text></a></g><path d="M420 40h20m-180 0a10 10 0 0 1 10 10v10a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M280 59h140v22H280z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="https://lightjason.github.io/AgentSpeak/rrd-output/html/org/lightjason/agentspeak/grammar/Agent.g4/index.htm#c857e06a9c6dcbcfd625e4859380c98e"><text x="350" y="74">UPPERCASELETTER</text></a></g><path d="M420 70a10 10 0 0 0 10-10V50a10 10 0 0 1 10-10m-180 0a10 10 0 0 1 10 10v40a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M280 100h20m100 0h20M300 89h100v22H300z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="https://lightjason.github.io/AgentSpeak/rrd-output/html/org/lightjason/agentspeak/grammar/Agent.g4/index.htm#a058c5dc734e54ff3d93b96acac339f4"><text x="350" y="104">UNDERSCORE</text></a></g><path d="M420 100a10 10 0 0 0 10-10V50a10 10 0 0 1 10-10m-180 0a10 10 0 0 1 10 10v70a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M280 130h40m60 0h40m-100-11h60v22h-60z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="https://lightjason.github.io/AgentSpeak/rrd-output/html/org/lightjason/agentspeak/grammar/Agent.g4/index.htm#999bbbf1d86bc7611397c77222e076db"><text x="350" y="134">DIGIT</text></a></g><path d="M420 130a10 10 0 0 0 10-10V50a10 10 0 0 1 10-10m-180 0a10 10 0 0 1 10 10v100a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M280 160h40m60 0h40m-100-11h60v22h-60z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="https://lightjason.github.io/AgentSpeak/rrd-output/html/org/lightjason/agentspeak/grammar/Agent.g4/index.htm#646da671ca01bb5d84dbb5fb2238dc8e"><text x="350" y="164">SLASH</text></a></g><path d="M420 160a10 10 0 0 0 10-10V50a10 10 0 0 1 10-10m0 0h10m-190 0a10 10 0 0 0-10 10v119a10 10 0 0 0 10 10m0 0h180m0 0a10 10 0 0 0 10-10V50a10 10 0 0 0-10-10m10 0h20m0 0h10m0 0h20m-10-10v20m10-20v20" transform="translate(.5 .5)"/></svg>

There are two kinds of variables

* Regular variables start with an **upper-case** letter
* The _garbage bin_ variable `_` can be used as a substitute in cases where the content of the variable is not of interest, but a variable has to be provided.

> **Note:** A more developer-specific explanation of variables can be found [$\to$ here](https://lightjason.github.io/knowledgebase/logicalprogramming/#variables).

**Example**

```prolog
NewDuration = 90
```