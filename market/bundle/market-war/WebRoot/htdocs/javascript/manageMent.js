function lister()
{
	if($("div.list")[0]!=undefined)
	{
		$("div.list table tbody tr:nth-child(even)").addClass("evens");
	}
}