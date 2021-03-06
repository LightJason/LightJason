"use strict";

jQuery(function() {

	// animates the svg image of the agent-finite-state-machine
	jQuery("#animate-agentfsm").click(function() {
		jQuery("#init").css("animation-name", "colorchange");
		jQuery("#init").css("animation-duration", "1.5s");

		jQuery("#main").css("animation-name", "colorchange");
		jQuery("#main").css("animation-duration", "3s");
		jQuery("#main").css("animation-delay", "2.75s");
		jQuery("#main").css("animation-iteration-count", "infinite");

		jQuery("#first, #second").css("animation-name", "colorchange");
		jQuery("#first, #second").css("animation-duration", "3s");
		jQuery("#first, #second").css("animation-delay", "4.25s");
		jQuery("#first, #second").css("animation-iteration-count", "infinite");
	} );

} );
