document.querySelectorAll(".comment__setting").forEach(setting => {
	setting.addEventListener("click", e => {
		let popup = setting.querySelector(".setting__pop-up");
		popup.style.display = popup.style.display == "block" ? "none" : "block";
	})
})

function deleteComment(post_id, comment_id) {
	const form = document.createElement("form");
	const input1 = document.createElement("input");
	const input2 = document.createElement("input");

	form.setAttribute("action", "deletecomment");
	form.setAttribute("method", "post");
	form.style.display = "none";

	input1.setAttribute("name", "comment_id");
	input1.value = comment_id;
	input2.setAttribute("name", "post_id");
	input2.value = post_id;

	form.appendChild(input1);
	form.appendChild(input2);
	document.body.appendChild(form);

	form.submit();
}
