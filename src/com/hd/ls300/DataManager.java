package com.hd.ls300;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.hd.base.Selectable;
import com.hd.base.Utils;
import com.hd.ls300.ConfigManager.ScanConfig;

public class DataManager {
	// 管理类
	public static class Manager implements Interface {
		private static final String TAG = "com.hd.ls300.DataManager.Manager";
		private List<Data> list = new ArrayList<Data>();

		public Manager() {
			Load(Internal.data_path);
		}
		
		public void Reload(){
			Load(Internal.data_path);
		}

		private static final boolean debug = true;

		private static List<Data> EnumData(String data_path) {
			List<Data> ret = new ArrayList<Data>();
			String ptDir = Internal.point_cloud_path;
			String imgDir = Internal.image_path;
			String metaDir = Internal.meta_path;
			if (debug) {
				List<String> ptFiles = Utils.getFiles(ptDir,
						Internal.file_endwith.point_cloud);
				for (String file : ptFiles) {
					String name = Utils.getFileName(file);
					String image_file = Utils.getFiles(imgDir, name,
							Internal.file_endwith.image);
					String meta_file = Utils.getFiles(metaDir, name,
							Internal.file_endwith.meta);
					Data data = null;
					if (meta_file == null) {
						MetaData meta = new MetaData("20130101", "Stuff",
								"103°，45°");
						ScanConfig cfg = Internal.config_mgr
								.Get("Panoramic-Low");
						data = Data.Load(name, file, image_file, cfg, meta);
						Utils.WriteSerializable(
								Utils.joinPath(metaDir, name + "."
										+ Internal.file_endwith.meta[0]), data);

					} else {
						data = (Data) Utils.ReadSerializable(meta_file);
					}
					ret.add(data);
				}
			} else {
				List<String> meFiles = Utils.getFiles(metaDir,
						Internal.file_endwith.meta);
				for (String meta_file : meFiles) {
					Data data = (Data) Utils.ReadSerializable(meta_file);
					ret.add(data);
				}
			}
			return ret;
		}

		@Override
		public void Load(String root) {
			list = EnumData(root);
		}

		@Override
		public Data Get(String name) {
			for (Data data : list)
				if (data.name.equalsIgnoreCase(name)) {
					return data;
				}

			return null;
		}

		@Override
		public Data Get(int idx) {
			return list.get(idx);
		}

		@Override
		public void Put(Data data) {
			// check data valid?
			list.add(data);
		}

		@Override
		public void Clear() {
			list.clear();
		}

		@Override
		public void Save(String root) {
			// TODO:保存数据，删除文件，新建文件？
			Log.i(TAG, "not implement yet.");
		}

		@Override
		public int Count() {
			return list.size();
		}

		private int cur = -1;

		@Override
		public void Select(int idx) {
			cur = idx % Count();
		}

		@Override
		public void Select(Object obj) {
			int len = list.size();
			for (int i = 0; i < len; i++)
				if (list.get(i).equals(obj)) {
					cur = i;
					break;
				}
		}
		
		@Override
		public void Select(String name) {
			int len = list.size();
			for (int i = 0; i < len; i++)
				if (list.get(i).name == name) {
					cur = i;
					break;
				}
		}

		@Override
		public Data GetSelect() {
			try {
				return list.get(cur);
			} catch (Exception e) {
				return null;
			}

		}

		@Override
		public int Select() {
			return cur;
		}

		@Override
		public int SelectPriv() {
			cur = cur <= 0 ? Count() - 1 : (cur - 1) % Count();
			return cur;
		}

		@Override
		public int SelectNext() {
			cur = cur < 0 ? 0 : (cur + 1) % Count();
			return cur;
		}

	}

	enum DataState {
		DATA_STATE_CREATED, DATA_STATE_SCANNING, DATA_STATE_DONE, DATA_STATE_DELETE,
	};

	// 采集数据描述
	public static class Data implements Serializable {
		static final long serialVersionUID = 7932287852182643444L;
		public String name;
		public String point_cloud_path;
		public String image_path;
		public ScanConfig config;
		public DataState state;
		public MetaData meta;// 可选项

		private Data(String name, String point_cloud_path, String image_path,
				ScanConfig cfg, MetaData meta, DataState state) {
			this.name = name;
			this.point_cloud_path = point_cloud_path;
			this.image_path = image_path;
			this.config = cfg;
			this.meta = meta;
			this.state = state;
		}

		public static Data Create(String name, String point_cloud_path,
				String image_path, ScanConfig cfg, MetaData meta) {
			return new Data(name, point_cloud_path, image_path, cfg, meta,
					DataState.DATA_STATE_CREATED);
		}

		public static Data Load(String name, String point_cloud_path,
				String image_path, ScanConfig cfg, MetaData meta) {
			return new Data(name, point_cloud_path, image_path, cfg, meta,
					DataState.DATA_STATE_DONE);
		}

		@Override
		public String toString() {
			return String.format(
					"Name:%s\n\nCloud:%s\n\nImage:%s\n\nConfig %s\n\n%s", name,
					point_cloud_path, image_path, config.toString(),
					meta.toString());
		}
	}

	// 元数据描述
	public static class MetaData implements Serializable {
		static final long serialVersionUID = 7932287852182643443L;
		public String date; // 采集时间
		public String stuff; // 采集人员
		public String location; // 地点

		public MetaData(String date, String stuff, String location) {
			this.date = date;
			this.stuff = stuff;
			this.location = location;
		}

		@Override
		public String toString() {
			return String.format("Date:%s\nStuff:%s\nlocation:%s", date, stuff,
					location);
		}
	}

	// 管理接口
	public static interface Interface extends Selectable {
		public void Load(String root);

		public Data Get(int idx);

		public void Save(String root);

		public Data Get(String name);

		public void Put(Data data);

		public int Count();

		public void Clear();

		public Data GetSelect();
	}

}
