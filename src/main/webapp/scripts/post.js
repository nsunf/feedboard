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
	const settingBtns = el.querySelectorAll(".setting__button");
	el.addEventListener("click", e => {
		e.preventDefault();
		if (![settingBtn, authorId, authorImg].includes(e.target) && ![...settingBtns].includes(e.target.parentElement) && !location.pathname.endsWith("/post")) {
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