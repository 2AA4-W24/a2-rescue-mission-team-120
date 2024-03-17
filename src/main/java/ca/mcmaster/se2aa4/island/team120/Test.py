1.	num_lanes = [1,2,3,4] # number of lanes in the intersection
2.	num_vehicles = 20 # initialize total cars at the intersection
3.	ambulance_pos = [0,200] # initialize ambulance
4.	directions = [NORTH, EAST, SOUTH, WEST]
5.	ambulance_dir = "EAST" # direction of ambulance
6.	signal_colours = [red, yellow, green, blue] 
7.	signal_duration = 20 
8.	stop_time = 10
9.	clear_time = 10
10.	travel_time = 0
11.	
12.	current_signal = signal_colours[0]
13.	ambulance_direction = directions[0] # starting north
14.	ambulance_lane = num_lanes[2] # first lane is for ambulance
15.	
16.	class SelfDrivingVehicle:
17.	     def simulate(self):
18.	     if deicision_made(): # if the car needs to turn, slow, change lanes, increase time
19.	          travel_time *= 1.2  # scale the total travel time by time taken to process an action
20.	
21.	     # update travel time based on clear time
22.	     travel_time += stop_time
23.	     travel_time += clear_time

24.	class Intersection:
25.	     def __init__(self):
26.	          self.signal_control = SignalControl()
27.	          self.ambulance = Ambulance()
28.	          self.vehicle = SelfDrivingVehicle()
29.	
30.	     # update intersection behaviour, ambulance, signal control, vehicles
31.	     def simulate(self):
32.	          self.assign_self_driving_vehicles()
33.	          self.update_signal_control()
34.	          self.update_ambulance()
35.	          self.simulate_self_driving_vehicles()

36.	     # assign self-driving vehicles to lanes
37.	     def assign_self_driving_vehicles(self):
38.	          for vehicles in range(num_lanes):
39.	               self.self_driving_vehicles.append(SelfDrivingVehicle())
40.	
41.	     # methods to update traffic signal control, ambulance/vehicle location and behaviour
42.	     def update_signal_control(self):
43.	          self.signal_control.update()
44.	     
45.	     def update_ambulance(self):
46.	          self.ambulance.update()
47.	
48.	     def simulate_self_driving_vehicles(self):
49.	          for vehicle in self.self_driving_vehicles:
50.	               vehicle.simulate()
51.	
52.	class SignalControl:
53.	     def __init__(self):
54.	          self.signal_duration = {"NORTH": 20, "EAST": 20, "SOUTH": 20, "WEST": 20}
55.	
56.	     def update(self):
57.	          traffic_flow_data = TrafficFlow().get_traffic_flow_data()
58.	
59.	          # take traffic flow data and change signal duration to manage larger congestions
60.	          for direction, flow in traffic_flow_data.items():
61.	               if flow == "high":
62.	                    self.increase_signal_time(direction)
63.	               elif flow == "low":
64.	                    self.decrease_signal_time(direction)
65.	
66.	     def increase_signal_time(self, direction):
67.	          if direction in self.signal_duration:
68.	               self.signal_duration[direction] += 4
69.	          if direction == "NORTH" or direction == "SOUTH":
70.	               self.signal_duration["EAST"] += 4
71.	               self.signal_duration["WEST"] += 4
72.	
73.	     def decrease_signal_time(self, direction):
74.	          if direction in self.signal_duration:
75.	               self.signal_duration[direction] -= 4
76.	          if direction == "NORTH" or direction == "SOUTH":
77.	               self.signal_duration["EAST"] -= 4
78.	               self.signal_duration["WEST"] -= 4
79.	
80.	class Ambulance:
81.	     def __init__(self):
82.	          self.location = ambulance_pos
83.	          self.priority_active = False
84.	          self.sensors_activated = False
85.	
86.	     # Method to update ambulance location and priority
87.	     def update(self):
88.	          self.update_location()
89.	
90.	     # update location of ambulance as it travels intersection (400x400 size)
91.	     def update_location(self):
92.	          if self.location[0] < 400:
93.	               self.location = (self.location[0] + 1, self.location[1])
94.	          # if ambulance reached priority activation point
95.	          if self.location[0] >= 50:
96.	               self.activate_sensors_and_lights()
97.	          # if ambulance reached end point
98.	          if self.location[0] >= 200:
99.	               self.deactivate_sensors_and_lights()
100.	
101.	# activate and deactivate emergency vehicles sensors
102.	def activate_sensors_and_lights(self):
103.	     self.sensors_activated = True
104.	     current_signal = signal_colours[3]
105.	
106.	def deactivate_sensors_and_lights(self):
107.	     self.sensors_activated = False
108.	     current_signal = signal_colours[0]
109.	
110.	class TrafficFlow:
111.	# report back live data of traffic activity in lanes
112.	     def get_traffic_flow_data(self):
113.	     return {"NORTH": "high", "EAST": "low", "SOUTH": "regular", "WEST": "high"}
114.	
115.	class GenerateReport:
116.	     def generate(self):
117.	     print()
