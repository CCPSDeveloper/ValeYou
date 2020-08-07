module.exports = {

	true_status:  function(res,body,msg)
	{
		res.status(200).json({
			'success':1,
			'code':200,
			'msg':msg,
			'data':body,
		});
		return false;
	},

	false_status: function(res, msg)
	{
		res.status(400).json({
			'success':0,
			'code':400,
			'msg':msg,
			'data':[],
		});
		return false;
	},
	wrong_status: function(res, msg)
	{
		res.status(400).json({
			'success':0,
			'code':400,
			'msg':msg,
			'data':{},
		});
		return false;
	},
	already_exist: function(res, msg)
	{
		res.status(409).json({
			'success':0,
			'code':409,
			'msg':msg,
			'data':{},
		});
		return false;
	},
	invalid_status: function(res, msg)
	{
		res.status(401).json({
			'success':0,
			'code':401,
			'msg':msg,
			'data':{},
		});
		return false;
	},
	unauth_status: function(res, msg)
	{
		res.status(401).json({
			'success':0,
			'code':401,
			'msg':msg,
			'data':{},
		});
		return false;
	}
	
}

