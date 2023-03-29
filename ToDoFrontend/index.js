//  <div class="todo-main">
//                                 <input type="checkbox">
//                                 <input type="text">
//                                 <select name="priority" id="priority">
//                                     <option value="mustdo">1</option>
//                                     <option value="shoulddo">2</option>
//                                     <option value="coulddo">3</option>
//                                     <option value="maybe">4</option>
//                                 </select>
//                                 <i class="fa-solid fa-pen-to-square"></i>
//                                 <i class="fa-solid fa-trash"></i>
                                
//                             </div> 

const url="http://127.0.0.1:8080/todoapp"
let form_login_change=document.querySelector("#login-change");
let form_signup_change=document.querySelector("#signup-change");
let login_form=document.getElementById("login")
let signup_form=document.getElementById("signup")
let add_button=document.getElementById("todobutton")
let cancel=document.getElementById("cancel")
let todoFor=document.getElementById("todo-form");


let user=JSON.parse(localStorage.getItem("user")) || null;
// console.log(user.id)
async function login(email,pass){
    let formMessage=document.querySelector("#form-signup-message");
    let obj={
        "email": email,
        "password": pass
      }
    
    let res=await fetch(url+"/user/login",{
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(obj)
      })
      
      if(res.ok){
          let data=await res.json()
          document.getElementById("info-name").innerHTML=data.userName.substring(0,20);
        formMessage.classList.add("none")
        data.password=pass;
        localStorage.setItem("user",JSON.stringify(data))
        user=data;
        disableAllForm()
        displayTodo(data.todoList)

      }else{
        formMessage.classList.remove("none")
      }
}
if(user!=null){
    login(user.email,user.password);
    
}




 
form_login_change.addEventListener("click",function (){
    login_form.classList.add("none")
    signup_form.classList.remove("none")
})

form_signup_change.addEventListener("click",function (){
    login_form.classList.remove("none")
    signup_form.classList.add("none")
    
})
login_form.addEventListener("submit",async(target)=>{
    target.preventDefault();
    let email=document.getElementById("login-email").value;
    let pass=document.getElementById("login-password").value;
    let formMessage=document.querySelector("#form-login-message");
    login(email,pass);
    document.getElementById("login-email").value="";
    document.getElementById("login-password").value="";

})
signup_form.addEventListener("submit",async(target)=>{
    target.preventDefault();
    let email=document.getElementById("signup-email").value;
    let pass=document.getElementById("signup-password").value;
    let name=document.getElementById("signup-name").value;
    let formMessage=document.getElementById("form-signup-message");
    let obj={
        userName:name,
        email: email,
        password: pass
      }
    
    let res=await fetch(url+"/user/signup",{
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(obj)
      })
      let data=await res.json();
      
      if(!res.ok){
        formMessage.classList.remove("none")
      }else{
        document.getElementById("signup-email").value="";
     document.getElementById("signup-password").value="";
    document.getElementById("signup-name").value="";
        formMessage.classList.add("none")
        login_form.classList.remove("none")
    signup_form.classList.add("none")
      }

})
function disableAllForm(){
    login_form.classList.add("none")
    signup_form.classList.add("none")
    let bg=document.querySelector(".bg");
    bg.classList.add("none");
}
function enableAllform(){
    login_form.classList.remove("none")
    let bg=document.querySelector(".bg");
    bg.classList.remove("none");
}

add_button.addEventListener("click",function(){
    add_button.classList.add("none");
    todoFor.classList.remove("none")
})

cancel.addEventListener("click",function(){
    let message=document.getElementById("form-todo-message");
    add_button.classList.remove("none");
    todoFor.classList.add("none")
    message.classList.add("none")
})
todoFor.addEventListener("submit",(target)=>{
target.preventDefault();
let desc=document.querySelector("#todo-desc").value
let prio=document.querySelector("#todoselect").value
obj={description:desc,
    type:prio}
    addTodo(user.id,obj)
})
async function addTodo(userid,obj){
    let message=document.getElementById("form-todo-message");
    let res=await fetch(url+`/user/${userid}/todo`,{
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(obj)
      });
      if(res.ok){
           let data=await res.json();
           user.todoList.push(data);
           localStorage.setItem("user",JSON.stringify(user))
           message.classList.remove("none")
           message.innerHTML="Todo added successfully"
           displayTodo(user.todoList)
      }else{
        message.classList.remove("none")
        message.innerHTML="Something went wrong"
      }
}


function displayTodo(todoList){
    

    let mustDo=document.querySelector("#todo-box-body-mustdo");
    let shouldDo=document.querySelector("#todo-box-body-shoulddo");
    let couldDo=document.querySelector("#todo-box-body-coulddo");
    let maybe=document.querySelector("#todo-box-body-maybe");
    mustDo.innerHTML="";
    shouldDo.innerHTML="";
    couldDo.innerHTML="";
    maybe.innerHTML="";
    todoList.forEach(element => {
        let no=1;
        if(element.type=="shouldo"){
            no=2;
        }else if(element.type=="couldo"){
            no=3
        }else if(element.type=="maybe"){
            no=4;
        }
        
        let flag=element.completed?"checked":"";
        let todoItem=`<div data-set-id=${element.todoId} class="todo-main">
        <input class="check${element.todoId}" type="checkbox" ${flag}  disabled>
        <input class="desc${element.todoId}" data-id=${element.todoId} value="${element.description}" disabled type="text">
        <select class="sel${element.todoId}" data-id=${element.todoId} disabled  name="priority" id="priority">
        <option value=${element.type}>${no}</option>
        <option value="mustdo">1</option>
            <option value="shouldo">2</option>
            <option value="couldo">3</option>
            <option value="maybe">4</option>
        </select>
        <i data-set-id=${element.todoId} class="fa-solid fa-pen-to-square"></i>
        <i data-set-id=${element.todoId} class="fa-solid fa-trash"></i>
    </div>`
    if(element.type=="mustdo"){
        mustDo.innerHTML+=todoItem;
    }else if(element.type=="shouldo"){
        shouldDo.innerHTML+=todoItem;
    }else if(element.type=="couldo"){
        couldDo.innerHTML+=todoItem;
    }else{
        maybe.innerHTML+=todoItem;
    }



});
let AllEdit=document.querySelectorAll(".fa-pen-to-square");


AllEdit.forEach((e)=>{
    e.addEventListener("click",()=>{
        
        let id=e.dataset.setId;
        let parent=e.parentElement.childNodes;
        parent[1].disabled=false
        parent[3].disabled=false
        parent[5].disabled=false
        e.classList.remove("fa-pen-to-square")
        e.classList.add("fa-check")
        let AllSave=document.querySelectorAll(".fa-check");
        AllSave.forEach((fa)=>{
            fa.addEventListener("click",()=>{
                let id=fa.dataset.setId;
                let parent=fa.parentElement.childNodes;
                parent[1].disabled=true
                parent[3].disabled=true
                parent[5].disabled=true
                fa.classList.add("fa-pen-to-square")
                fa.classList.remove("fa-check")
               let desc= document.querySelector(`.desc${id}`).value;
               let checkVal= document.querySelector(`.check${id}`).checked;
               let selVal= document.querySelector(`.sel${id}`).value
               
               let obj={
                todoId:id,
                description:desc,
                completed:checkVal,
                type:selVal
               }
               updateTodo(user.id,id,obj)
            })
        })
        
    })
    
})
let AllDelete=document.querySelectorAll(".fa-trash");
    AllDelete.forEach((g)=>{
        g.addEventListener("click",function(){
            deleteTodo(user.id,g.dataset.setId)

        })
    })


}

async function updateTodo(userId,todoId,obj){
    let res=await fetch(url+`/user/${userId}/todo/${todoId}`,{
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(obj)
      });
      if(res.ok){
           let data=await res.json();
           for(let i=0;i<user.todoList.length;i++){
                if(user.todoList[i].todoId==data.todoId){user.todoList[i]=data
                break}
                
           }
           
           localStorage.setItem("user",JSON.stringify(user))
           displayTodo(user.todoList)
      }else{
       window.alert("something went wrong")
      }
}

async function deleteTodo(userId,todoId){
    let res=await fetch(url+`/user/${userId}/todo/${todoId}`,{
        method: 'DELETE'
      });
      if(res.ok){
           let data=await res.json();
           for(let i=0;i<user.todoList.length;i++){
                if(user.todoList[i].todoId==data.todoId){user.todoList.splice(i,1)
                break}
                
           }
           localStorage.setItem("user",JSON.stringify(user))
           displayTodo(user.todoList)
      }else{
       window.alert("something went wrong")
      }
}


let logoutbutton=document.querySelector("#logoutbutton");
logoutbutton.addEventListener("click",()=>{
    enableAllform();
    user=null;
    localStorage.removeItem("user");
})
