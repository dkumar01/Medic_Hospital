/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medic_hospital;

/**
 *
 * @author Deepak Kumar
 */
public class StaticData 
{
    private static int doctorId;
    
    
    public void setDoctorId(int id)
    {
        doctorId = id;
    }
    
    public int getDoctorId()
    {
        return doctorId;
    }
}
