const texts = ["идей", "проектов", "историй"];
let index = 0;

const changingText = document.getElementById("changing-text");
const nextText = document.getElementById("next-text");

function changeText() {
    nextText.textContent = texts[(index + 1) % texts.length];

    changingText.classList.add("animate-slide-up-out");
    nextText.classList.add("animate-slide-up-in");

    setTimeout(() => {
        changingText.textContent = nextText.textContent;

        changingText.classList.remove("animate-slide-up-out");
        nextText.classList.remove("animate-slide-up-in");

        index = (index + 1) % texts.length;
    }, 500);
}

setInterval(changeText, 2000);

function toggleMenu() {
    const burger = document.querySelector("#burger");
    const menu = document.querySelector("#mobile-menu");
    const body = document.querySelector("body");

    burger.addEventListener("click", () => {
        burger.classList.toggle("active");
        menu.classList.toggle("hidden");
        menu.classList.toggle("flex");
        body.classList.toggle("overflow-hidden");
    });

    window.addEventListener("resize", () => {
        if (window.innerWidth > 767.99) {
            menu.classList.add("hidden");
            menu.classList.add("flex");
            burger.classList.remove("active");
            body.classList.remove("overflow-hidden");
        }
    });
}
toggleMenu();
