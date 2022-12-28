const frm = document.editform;
const imgInput = frm.images;

let fileLoaded = true;
let imgArr = [];

imgInput.addEventListener("change", e => {
		e.preventDefault();
		fileLoaded = false;
		const files = imgInput.files;
		
		for (let i = 0; i < files.length; i++) {
			const file = files[i];

			const fr = new FileReader();
			fr.readAsDataURL(file);
			fr.addEventListener("loadend", e => {
				const img = document.createElement("img");
				img.setAttribute("src", e.target.result);
				document.querySelector(".edit-box").appendChild(img);
				imgArr.push(e.target.result);
				if (imgArr.length == files.length) fileLoaded = true;
			})
		}
	})

frm.addEventListener("submit", e => {
	e.preventDefault();
	if (fileLoaded) {
		//frm["image-base64"].setAttribute("value", imgArr[0]);
		frm["image-base64"].innerText = imgArr[0].slice(0, 5000);
		frm.submit();
	} else {
		alert("images are loading. try again later");
	}
})