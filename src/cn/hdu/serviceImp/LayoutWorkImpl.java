package cn.hdu.serviceImp;

import java.util.List;

import cn.hdu.service.LayoutWork;
import cn.hdu.timentity.Location;
import cn.hdu.timentity.Template;

public class LayoutWorkImpl implements LayoutWork{
    public final static int Gap_X = 5; 
    public final static int Gap_Y = 5;
	
    public final static int Location_X = 100;
    public final static int Location_Y = 50;
	
	public void EnvironmentLayout(Template template) {
		// TODO Auto-generated method stub
		List<Location> locations = template.getLocations();
		Location waitLocation = locations.get(0);
		locations.remove(0); //ÒÆ³ýµÚÒ»¸ö
		
		for(int i = 0;i < locations.size();i++) {
			Location location = locations.get(i);
			
			int x = Location_X - Gap_X * i;
			int y = Location_Y + Gap_Y * i;
			
			location.setX(String.valueOf(x));
			location.setY(String.valueOf(y));
		}
		
	}

	@Override
	public void ProbeLayout(Template template) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PlanLayout(Template template) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void TemplateLayout(Template template) {
		// TODO Auto-generated method stub
		
	}

}
