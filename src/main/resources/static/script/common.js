// Topmenu_Bar
function TopMenu__Bar(){
  $(function () {
    $(".menu > li > a.cate").click(function (e) {
        e.preventDefault();  
        const submenu = $(this).next(".subMenu");
        const isActive = submenu.hasClass("active");
  
        $(".menu > li > a.cate").removeClass("active");
        $(".subMenu").removeClass("active").slideUp(200);
  
        if (!isActive) {
            $(this).addClass("active");
            submenu.addClass("active").slideDown(200); 
        }
    });
  
    // 닫기 버튼 클릭 시 서브메뉴 닫기
    $(".menuArea").on("click", ".close-submenu", function (e) {
        e.preventDefault();
        $(this).closest(".subMenu").removeClass("active").slideUp(200); 
        $(this).closest(".menu").find(".cate").removeClass("active"); 
    });
  
    $(".menuArea").on("click", ".subMenu-3 > li > a", function (e) {
        e.preventDefault(); 
        const submenu = $(this).closest(".subMenu"); 
        const titleElement = submenu.find(".sub p.title"); 

        submenu.find(".sub p.title").removeClass("active");

        titleElement.addClass("active");
  
        submenu.find(".subMenu-3 > li > a").removeClass("active");
        $(this).addClass("active");
    });
  });  
}
TopMenu__Bar();

// 체크 박스 
$('.check_All').change(function() {
  if ( this.checked ) {
    $('.check_Item:not(:checked)').prop('checked', true);
  }
  else {
    $('.check_Item:checked').prop('checked', false);
  }
});

$('.check_Item').change(function() {
  let allChecked = $('.check_Item:not(:checked)').length == 0;
  $('.form-1__checkbox-all').prop('checked', allChecked);
});

// On_off ClickEvent
$(function () {
  $(".btn").click(function () {
    $(".btn").removeClass("on");
    $(this).addClass("on");
  });
});

$(function () {
  $(".bookMark i").click(function () {
    $(".bookMark i").removeClass("on");
    $(this).addClass("on");
  });
});

$(function () {
  $(".filterName li ").click(function () {
    $(".filterName li").removeClass("on");
    $(this).addClass("on");
  });
});

// filterList
function Filter__List (){
  const table_filterList = document.querySelectorAll(".filter__name");

  for (let i = 0; i < table_filterList.length; i++) {
      table_filterList[i].addEventListener("click", () => {
      table_filterList[i].classList.toggle("active");
  });

      table_filterList[i].addEventListener("keyup", (e) => {
          if (e.keyCode === 13) {
              table_filterList[i].classList.toggle("active");
          }
      });
  }
}
Filter__List ();

// userProfile
function User__Profile (){
    document.getElementById('profileImage').addEventListener('click', function() {
      document.getElementById('fileInput').click();
  });

  document.getElementById('fileInput').addEventListener('change', function(event) {
      const file = event.target.files[0];
      if (file) {
          const reader = new FileReader();
          reader.onload = function(e) {
              const imagePreview = document.getElementById('imagePreview');
              imagePreview.src = e.target.result;
              imagePreview.classList.add('show'); // 이미지가 업로드되면 클래스 추가
              document.getElementById('uploadText').style.display = 'none'; // 텍스트 숨기기
          };
          reader.readAsDataURL(file);
      }
  });
}
User__Profile();

// click-> menuplus
function Menuplus (){
  // DOMContentLoaded 이벤트를 사용하여 DOM이 로드된 후에 JavaScript 코드 실행
  document.addEventListener("DOMContentLoaded", function() {
      // 삭제 버튼(i 태그)에 클릭 이벤트를 추가하여 input__flex 클래스가 삭제되도록 처리
      let deleteButtons = document.getElementsByName("del");
      deleteButtons.forEach(function(button) {
          button.addEventListener("click", function() {
              let inputFlex = this.closest(".input__flex");
              inputFlex.remove();
              checkInputFlex();
          });
      });

      // 추가 버튼에 클릭 이벤트를 추가하여 input__flex 클래스를 추가하는 기능 구현
      let addButton = document.getElementById("addMenu");
      addButton.addEventListener("click", function() {
          let inputMenu = document.querySelector(".input_menu");
          let newInputFlex = document.createElement("div");
          newInputFlex.classList.add("input__flex", "mb10");
          newInputFlex.innerHTML = `
              <div class="flex_02">
                  <input type="checkbox" name="" class="form-1__checkbox-all">
                  <span class="ml5"> 대표메뉴</span>
              </div>
              <div class="input_box02">
                  <input type="text" name="menuName" class="menuName d_gray" placeholder="메뉴명">
                  <input type="text" name="menuPrice" class="menuPrice ml10 d_gray" placeholder="가격">
              </div>
              <i class="fa-solid fa-minus cs_p" name="del"></i>
          `;
          inputMenu.insertBefore(newInputFlex, inputMenu.lastElementChild);
          
          // 새로 추가된 삭제 버튼에 클릭 이벤트를 추가하여 input__flex 클래스가 삭제되도록 처리
          let newDeleteButton = newInputFlex.querySelector("i[name='del']");
          newDeleteButton.addEventListener("click", function() {
              newInputFlex.remove();
              checkInputFlex();
          });

          checkInputFlex();
      });

      // 입력 요소가 모두 삭제되었을 때 butArea의 margin-left 값을 0으로 변경하는 함수
      function checkInputFlex() {
          let inputFlexes = document.querySelectorAll(".input__flex");
          let butArea = document.querySelector(".butArea");
          if (inputFlexes.length === 0) {
              butArea.style.marginLeft = "0";
          } else {
              butArea.style.marginLeft = "10.4rem"; // 기본값
          }
      }

      // 페이지 로드 시 한 번 실행하여 초기 상태를 체크
      checkInputFlex();
  });
}
Menuplus();

// map_searchPopup
function Modal_add (){
    // 주소검색 버튼 클릭 시 모달 열기
    document.getElementById('addressSearchBtn').addEventListener('click', function() {
      var modal = document.getElementById('myModal');
      modal.style.display = 'block';
    });
    
    // 모달에서 닫기 버튼 클릭 시 모달 닫기
    document.getElementsByClassName('close')[0].addEventListener('click', function() {
      var modal = document.getElementById('myModal');
      modal.style.display = 'none';
    });
    
    // 검색 버튼 클릭 시 주소 검색 기능 추가 (예시로 alert 창 표시)
    document.getElementById('searchButton').addEventListener('click', function() {
      var address = document.getElementById('addressInput').value;
      alert('주소 검색 기능 추가: ' + address); // 여기에 실제 주소 검색 기능을 추가할 수 있습니다.
    });
}
Modal_add();