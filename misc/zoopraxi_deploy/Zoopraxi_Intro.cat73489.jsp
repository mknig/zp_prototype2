<%@page import="java.net.URLEncoder"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/mkoenig-html.tld" prefix="html_mk"%>
<%@ taglib uri="/WEB-INF/tld/mkoenig-logic.tld" prefix="logic_mk"%>
<%@page import="java.util.*"%>
<%@page import="de.tw.common.*"%>
<%@page import="de.tw.common.enumeration.*"%>
<%@page import="de.tw.ejb.*"%>
<%@page import="de.tw.web.struts.shop.form.*"%>
<%@page import="de.mkoenig.navigation.I_NavigationHandler"%>
<%@page import="de.mkoenig.taglib.URLWriterTag"%>
<%@page import="de.mkoenig.tools.configproperties.FileConfigProperties"%>
<%@page import="de.mkoenig.tools.Utilities"%>
<%@page import="de.mkoenig.tools.MK_SortedList"%>
<%@page import="de.tw.common.comparator.Comparator_Categories_BY_RANK"%>
<%@page import="de.mkoenig.tools.sort.ReflectionSorter"%>
<%@page
	import="de.tw.common.comparator.Comparator_Categories_BY_RANK_PARENT"%>
<%@page import="de.tw.common.comparator.Comparator_Common"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="de.mkoenig.taglib.FormatTag"%>
<%@page import="de.mkoenig.tools.performance.PerformanceMeasure"%>
<%@ taglib uri="/WEB-INF/tld/JSTL/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/JSTL/c-rt.tld" prefix="c_rt"%>




<head>
<title>
ZooPraxi.Beta - Tapeten | TapetenAgentur - im Online Shop kaufen
</title>
<link rel="canonical" href="http://www.tapetenagentur.de/Tapeten-603/EigenHeim-ZooPraxi-Beta.rf"/>
<meta name="google" content="notranslate"/>
<meta name="verify-v1" content="WoIlR8ng8wRbI9gMw7ZhTutYFWmxJB0crLFkVVqAhvY="/>
<meta name="google-site-verification" content="oe-cSFDVRlVO95Rq12uxybu8dqaA1DeBt6zwiWwh2vQ"/>
<link rel="shortcut icon" href="http://www.tapetenagentur.de/mb_ta/html/favicon.ico" type="image/x-icon"/>
<meta name="description" content="ZooPraxi.Beta  Tapeten - im Online Shop kaufen - Hier findest Du Deine  ZooPraxi.Beta  Tapete">
<meta name="keywords" content="products,tapeten,zoopraxi.what?Tapeten, Tapete , K? onlineshop">
<base href='http://www.tapetenagentur.de/mb_ta/'/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="robots" content="index,follow"/>
<meta property="fb:page_id" content="163905255207"/>
<meta property="fb:admins" content="1477549582"/>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/_mb.js/mb.js"></script>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery-1.6.2.min.js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery-ui-1.8.15/jquery-ui.min.1.8.15.js"></script>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery_form.js"></script>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery.form-defaults/jquery.form-defaults.js"></script>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery.mb.menu.2.8.5/inc/jquery.metadata.js"></script>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery.mb.menu.2.8.5/inc/jquery.hoverIntent.js"></script>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery.mb.menu.2.8.5/inc/mbMenu.js"></script>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery_tipTipv13/jquery.tipTip.js"></script>
<script src="http://static.tapetenagentur.de/mb_ta_war/res/js/selectmenu/ui.selectmenu.js" type="text/javascript"></script>
<script src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery.jqfancytransitions/jqFancyTransitions.1.8.js" type="text/javascript"></script>
<script src="http://static.tapetenagentur.de/mb_ta_war/res/js/magicthumb/magicthumb/magicthumb.js" type="text/javascript"></script>
<script src="http://static.tapetenagentur.de/mb_ta_war/res/js/SliderWall/SliderWall-Templates/sliderwall-bullets-1.1.2.js"></script>
<link rel="stylesheet" type="text/css" href='http://static.tapetenagentur.de/mb_ta_war/css/nexus_norm.css'/>
<link rel="stylesheet" type="text/css" href='http://static.tapetenagentur.de/mb_ta_war/css/nexus_default_shop.css'/>
<link rel="stylesheet" type="text/css" href='http://static.tapetenagentur.de/mb_ta_war/css/nexus_tapetenBorder.css'/>
<link rel="stylesheet" type="text/css" href='http://static.tapetenagentur.de/mb_ta_war/css/nexus_typo.css'/>
<link type="text/css" href="http://static.tapetenagentur.de/mb_ta_war//css/jquery/jquery-ui-1.8.14.custom/css/custom-theme/jquery-ui-1.8.14.custom.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="http://static.tapetenagentur.de/mb_ta_war//css/menu_black.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="http://static.tapetenagentur.de/mb_ta_war//css/tipTip.css" media="screen"/>
<link href="http://static.tapetenagentur.de/mb_ta_war/res/js/magicthumb/magicthumb/magicthumb.css" rel="stylesheet" type="text/css" media="screen"/>
<div id="waiting" style=visibility:hidden>
loading....<img src="res/img/ajax-loader.gif" alt="loading..."/>
</div>
<script src="http://www.google-analytics.com/urchin.js" type="text/javascript"></script>
<script type="text/javascript">_uacct="UA-515146-1";urchinTracker();</script>
<script type="text/javascript">var _gaq=_gaq||[];_gaq.push(['_setAccount','UA-515146-1']);_gaq.push(['_gat._anonymizeIp']);_gaq.push(['_trackPageview']);_gaq.push(['_trackPageLoadTime']);(function(){var ga=document.createElement('script');ga.type='text/javascript';ga.async=true;ga.src=('https:'==document.location.protocol?'https://ssl':'http://www')+'.google-analytics.com/ga.js';var s=document.getElementsByTagName('script')[0];s.parentNode.insertBefore(ga,s);})();</script>
</head>




<%
	I_NavigationHandler nav = (I_NavigationHandler) session
			.getAttribute(TWConsts.NAVIGATIONHANDLER);
	BusinessDelegate bd = BusinessDelegate.getInstance();
	
%>


<%-- CHECK JAVA.Installed --%>
<%-- CHECK JAVA.Installed --%>
<%-- CHECK JAVA.Installed --%>
<%-- CHECK JAVA.Installed --%>
<%-- CHECK JAVA.Installed --%>
<%-- CHECK JAVA.Installed --%>
<script language="javascript" type="text/javascript">

$(document).ready(function(){
	
	var browserInfo = navigator.userAgent;
	var jvmInstalled = 0;
	
	// variable to check if client is MSIE
	isIE = "false";
	//alert("browserInfo" + browserInfo );
	
	if(browserInfo.indexOf("MSIE")== -1 ){
	
		var index;
		var plugins=navigator.plugins;
		var plugin;
		for (index in plugins) {
			plugin=plugin + plugins[index].description  + "<br>";
			console.log("installed PlugIN:" + plugins[index].description );
		} 
		//alert("PlugIns:" + plugin);
		
		
		var mineTypes=navigator.mimeTypes;
		var erg;
		for (index in mineTypes) {
		    try {
			    erg=mineTypes[index].type  + "<br>";
			    //alert("supportMimeType:" + mineTypes[index].type);
			    //document.write(erg)
			   	console.log("support MimeType:" + mineTypes[index].type );
			    if(mineTypes[index].type.indexOf("JNLP") != -1 
			    		|| mineTypes[index].type.indexOf("jnlp") != -1
			    		|| mineTypes[index].type.indexOf("x-java-applet") != -1
			    	){
			    	jvmInstalled=1;
			    
			    	//break;
			    }
		    }
		    catch (e) {
        	//alert("error"+e );
        }
		} 
	
	
	}else{
		//alert("MSIE found");
		//var pluginObject = new ActiveXObject("java");
		// Create Java Web Start ActiveX object
		var x = new ActiveXObject('JavaWebStart.isInstalled');
		//alert("MSIE found" + x );
		try {
            var jws = new ActiveXObject('JavaWebStart.isInstalled');
            jvmInstalled=1;
            // alert("MSIE found JAVA" );
        }
        catch (e) {
        //	alert("MSIE found NO_JAVA" );
        }
		
	}
	
	//alert("XXX" + jvmInstalled);
	if(jvmInstalled==1){
	 	$("#div_JavaInstalled").append("<li>Java ab Version 1.6");
	}else{
		//alert("XXX");
	//	$("#div_JavaInstalled").append("<div class='h2' style='padding-top:20px;'><a href='http://www.java.com/de/download/' style='color:red;'>bitte installiere Java und rufe die Seite anschlie?nd erneut auf...weiter</a></div>");
		//$("#zpStart").hide();
	}
	
	
	
});	
</script>


<table border="0">

	<%-- row 2 Navi...--%>
	<tr>
		<td>
			<table class="shadowWhite">
				
				<%-- content... --%>
				<tr>
					<td valign="top">
					
						<div style="padding: 5px 5px 5px 5px">
							
							<%-- Slider... --%>
							<%-- Slider... --%>
							<%-- Slider... --%>	
							<div style="clear:left; padding-top: 20px; padding-bottom: 10px;">
							<jsp:include page="/html/de/mb_templates/zoopraxi/Zoopraxi_SlideShow.jsp"/>	
							</div>

						</div>
					</td>
				</tr>
				
				
				
				
			</table></td>
	</tr>

	



</table>

<%-- ARCHIVE--%>
