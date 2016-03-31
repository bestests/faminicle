const express = require("express");
const mysql   = require("mysql");
const http    = require("http");
const path    = require("path");

const app = express();
app.use(express.static(path.join(__dirname, "public")));

const httpServer = http.createServer(app).listen(2000, (req, res) => {
  console.log("2000 소켓 서버 시작 됨");
});

const io = require("socket.io").listen(httpServer);

io.sockets.on("connection", (socket) => {
  socket.emit("toClient", {msg: "Welcome"});
  socket.on("fromClient", (data) => {
    socket.broadcast.emit("teClient", data);
    socket.emit("toClient", data);
    console.log("클라이언트에서 온 메시지 : " + data.msg);
  });
});

const pool = mysql.createPool({
  connectionLimit: 100,
  host           : "localhost",
  user           : "java77",
  password       : "1111",
  database       : "faminicle_db",
  debug          : false
});

app.get("/", (req, res) => {
	res.send("Hello World");
});

// 가족 신청 시 아이디 체크
app.get("/checkId", (req, res) => {
	var reqId    = req.param("chkId");
	var callback = req.param("callback");
	
	pool.getConnection(function(err,connection){
       connection.query(
		   'select * from fam_member where id =' + mysql.escape(reqId), 
		   function (err, rows) {
	           if(err){
	               connection.release();
	               throw err;
	           }
	           console.log(rows);
	           if(rows.length == 1) {
	        	  res.send(callback + 
	        			   "({result: '" + rows[0].ID + 
	        			   "', thumbPic: '" + rows[0].MEM_PIC_PATH + 
	        			   "', resIdNo: '" + rows[0].MEM_NO + "'})"); 
	           } else {
	        	   res.send(callback + "({result: '해당 아이디가 존재하지 않습니다.'})");
	           }
	          
	           connection.release();
	       }
       );
   });
});

// 가족 신청
app.get("/reqFam", (req, res) => {
	var reqId    = req.param("reqId");
	var resId    = req.param("resId");
	var reqIdNo  = req.param("reqIdNo");
	var resIdNo  = req.param("resIdNo");
	var famName  = req.param("famName");
	var callback = req.param("callback");
	
	pool.getConnection((err, connection) => {
		connection.query(
			"INSERT INTO FAM_REQUEST(FAM_REQ_ID_NO, FAM_RES_ID_NO, FAM_NAME)" +
			"VALUES(" + mysql.escape(reqIdNo) + ", " + mysql.escape(resIdNo) + "," + mysql.escape(famName) + ")",
			(err, rows) => {
				if(!err) {
					console.log("Query success!!");
					res.send(callback + 
							 "({ reqId:'"   + reqId   + 
							 "', resId:'"   + resId   + 
							 "', famName:'" + famName + 
							 "', reqIdNo:'" + reqIdNo + 
							 "', resIdNo:'" + resIdNo + "'})");
				} else {
					console.log(err);
					connection.release();
				}
			}
		);
		connection.release();
	}); 
});

// 

io.sockets.on("connection", function (socket) {
	socket.on("reqFam", (data) => {
		console.log("reqFam socket request");
		console.log("reqId   : " + data.reqId);
		console.log("resId   : " + data.resId);
		console.log("famName : " + data.famName);
		socket.broadcast.emit("resFam", {reqId: data.reqId, resId: data.resId, famName: data.famName, reqIdNo: data.reqIdNo, resIdNo: data.resIdNo});
		socket.emit("resFam", {reqId: data.reqId, resId: data.resId, famName: data.famName, reqIdNo: data.reqIdNo, resIdNo: data.resIdNo});
		console.log("reqFam socket exit");
	});
//	socket.emit('famReq', {id: resId, reqId: reqId, famName: famName});
});
