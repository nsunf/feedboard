const frm = document.editform;
const imgInput = frm.images;

let fileLoaded = true;
let loadCnt = 0;
/*
imgInput.addEventListener("change", e => {
		e.preventDefault();
		fileLoaded = false;
		loadCnt = 0;
		document.querySelectorAll(".editform__img-box .base64").forEach(el => el.remove());
		document.querySelectorAll(".editform__img-box .base64-img").forEach(el => el.remove());

		const files = imgInput.files;
		
		for (let i = 0; i < files.length; i++) {
			const file = files[i];

			const fr = new FileReader();
			fr.readAsDataURL(file);
			fr.addEventListener("loadend", e => {
				const base64 = e.target.result;
				const imgBox = document.querySelector(".editform__img-box");

				const inputEl = document.createElement("input");
				inputEl.classList.add("base64");
				inputEl.setAttribute("type", "hidden");
				inputEl.setAttribute("name", "base64");
				inputEl.setAttribute("value", base64);

				const imgEl = document.createElement("img");
				imgEl.classList.add("base64-img");
				imgEl.setAttribute("src", base64);

				imgBox.appendChild(imgEl);
				imgBox.appendChild(inputEl);

				loadCnt++;
				if (loadCnt == files.length) fileLoaded = true;
			})
		}
	})
*/

frm.addEventListener("submit", e => {
	e.preventDefault();
	//if (fileLoaded) {
	if (true) {
		frm.submit();
	} else {
		alert("images are loading. try again later");
	}
})