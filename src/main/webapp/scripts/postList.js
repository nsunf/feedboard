const postSettings = document.querySelectorAll(".post__setting");

postSettings.forEach(postSetting => {
	const settingBtn = postSetting.querySelector(".setting__icon");
	const popup = postSetting.querySelector(".setting__pop-up");
	
	settingBtn.addEventListener("click", e => {
		e.preventDefault();
		popup.style.display = popup.style.display == 'block' ? 'none':'block';
	})
})

document.querySelectorAll(".post:not(.not-post)").forEach(el => {
	const post_uuid = el.getAttribute("data-post_id");
	const settingBtn = el.querySelector(".setting__icon");
	const authorId = el.querySelector(".author__id");
	const authorImg = el.querySelector(".author__img");
	const likesBtn = el.querySelector(".post__likes");
	const likesIcon = el.querySelector(".post__likes > i");
	const likesCount = el.querySelector(".post__likes > span")
	const settingBtns = el.querySelectorAll(".setting__button");
	el.addEventListener("click", e => {
		e.preventDefault();
		if (![settingBtn, authorId, authorImg, likesBtn, likesIcon, likesCount].includes(e.target) && ![...settingBtns].includes(e.target.parentElement) && !location.pathname.endsWith("/post")) {
			location.href = "/FeedBoard/post?id=" + post_uuid;
		}
	})
})

document.body.addEventListener("click", e => {
	const settingBtns = document.querySelectorAll(".post .post__setting");
	if (![...settingBtns].includes(e.target.parentElement)) {
		document.querySelectorAll(".post__setting .setting__pop-up").forEach(popup => {
			popup.style.display = "none";
		})
	}
})

function requestPost(path, post_uuid) {
	if (path == 'deletepost' && !confirm("really???")) return; 
	const form = document.createElement("form");
	const input = document.createElement("input");
	
	form.setAttribute("action", path);
	form.setAttribute("method", "post");
	form.style.display = "none";
	
	input.setAttribute("name", "post_uuid");
	input.value = post_uuid;
	
	form.appendChild(input);
	document.body.appendChild(form);
	
	form.submit();
}

function toggleLike(post_uuid, on) {
	const form = document.createElement("form");
	const postIdInput = document.createElement("input");
	const onInput = document.createElement("input");
	
	form.setAttribute("action", "like");
	form.setAttribute("method", "post");
	form.style.display = "none";
	
	postIdInput.setAttribute("name", "post_uuid");
	postIdInput.value = post_uuid;
	
	onInput.setAttribute("name", "on");
	onInput.value = on ? 1 : 0;
	
	form.appendChild(postIdInput);
	form.appendChild(onInput);
	document.body.appendChild(form);
	
	form.submit();
}